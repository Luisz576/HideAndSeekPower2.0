package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.game.events.PlayerJoinInGameEvent;
import com.luisz.hideandseekpowers.game.events.PlayerQuitOfGameEvent;
import com.luisz.hideandseekpowers.game.power.GamePowerController;
import com.luisz.hideandseekpowers.game.power.Power;
import com.luisz.hideandseekpowers.game.power.powers.Invisibility;
import com.luisz.hideandseekpowers.game.scoreboard.GameScoreboard;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class Game{
    public static final int TIME_TO_START = 30,
            TIME_TO_HIDE = 60,
            TIME_TO_FINISH = 15*60,
            TIME_TO_CLOSE = 20;

    private final GameScoreboard gameScoreboard;
    private final Arena arena;
    public String getArenaName(){
        return arena.name;
    }
    public Location getLobby(){ return arena.lobby; }
    public Location getSpawn(){ return arena.spawn; }
    public World getWorld(){ return arena.getWorld(); }
    private final GamePowerController gamePowerController;
    public GamePowerController getGamePowerController() {
        return this.gamePowerController;
    }
    private GameState gameState;
    public GameState getGameState() {
        return this.gameState;
    }

    private final GameListener gameListener;

    //placar
    private final HashMap<Player, Integer> placar = new HashMap<>();
    public void addPointTo(Player player){
        placar.put(player, placar.get(player) + 1);
    }

    private final List<Player> procuradores = new ArrayList<>(),
            escondedores = new ArrayList<>(),
            espectadores = new ArrayList<>();

    public List<Player> getProcuradores(){
        return new ArrayList<>(procuradores);
    }
    public List<Player> getEscondedores(){
        return new ArrayList<>(escondedores);
    }
    public List<Player> getEspectadores(){
        return new ArrayList<>(espectadores);
    }

    public int getAmountOfPlayers(){
        return escondedores.size() + procuradores.size();
    }

    public int getMaxPlayers(){
        return arena.maxPlayers;
    }

    private final int runEachSecondId;
    private int time;
    public int getCurrentTime(){
        return time;
    }

    public boolean isPlayerInsideThisGame(Player player) {
        return this.procuradores.contains(player) || this.escondedores.contains(player) || this.espectadores.contains(player);
    }
    public boolean isEscondedor(Player player){
        return this.escondedores.contains(player);
    }

    public Game(Arena arena){
        this.arena = arena;
        this.gameState = GameState.RECRUITING;
        this.time = TIME_TO_START;
        this.gamePowerController = new GamePowerController();
        this.gameScoreboard = new GameScoreboard(this);
        this.runEachSecondId = Main.sc.scheduleSyncRepeatingTask(Main.instance, this::runEachSecond, 0, 20L);
        Main.gameController.add(this);
        this.arena.getWorld().setDifficulty(Difficulty.PEACEFUL);
        this.gameListener = new GameListener(this);
        Main.pm.registerEvents(this.gameListener, Main.instance);
    }

    private void runEachSecond(){
        gameScoreboard.updateRender();
        switch (this.gameState){
            case RECRUITING:
                if(time <= 0)
                    startHiding();
                else if(time < 10)
                    sendMessageToAll(ChatColor.YELLOW + "O jogo começa em " + time + " segundo(s)!");
                break;
            case HIDING:
                if(time <= 0)
                    startGame();
                else if(time < 10)
                    sendMessageToAll(ChatColor.YELLOW + "A fera sairá em " + time + " segundo(s)!");
                break;
            case GAMEPLAY:
                if(time <= 0)
                    finishGame(true);
                else if(time < 10)
                    sendMessageToAll(ChatColor.YELLOW + "O jogo acaba em " + time + " segundo(s)!");
                else if(time % 60 == 0 && time != TIME_TO_FINISH) {
                    sendMessageToAll(ChatColor.YELLOW + "O jogo acaba em " + (time / 60) + " minuto(s)!");
                    if(time % (60*2) == 0){
                        procuradores.get(new Random().nextInt(procuradores.size())).getInventory().addItem(GameItems.getSnowball(1));
                    }else if(time % (60*4) == 0 || time == 60){
                        procuradores.get(new Random().nextInt(procuradores.size())).getInventory().addItem(GameItems.getGlowstoneDust(1));
                    }
                }
                break;
            case STOPING:
                if(time <= 0)
                    closeGame();
                else if(time < 10)
                    sendMessageToAll(ChatColor.YELLOW + "O jogo finaliza em " + time + " segundo(s)!");
                break;
        }
        time--;
    }

    //STATES
    private void startHiding(){
        this.gameState = GameState.HIDING;
        this.time = TIME_TO_HIDE;
        for(Player p : escondedores) {
            p.teleport(arena.spawn);
            gamePowerController.usePower(new Invisibility(this, p, p.getLocation()));
        }
        for(Player p : espectadores)
            p.teleport(arena.spawn);
        // if something just happens
        for(Player p : procuradores)
            p.teleport(arena.spawn);
    }
    private void startGame(){
        this.gameState = GameState.GAMEPLAY;
        this.time = TIME_TO_FINISH;
        gamePowerController._resetAllDelays();
        sendMessageToAll(ChatColor.YELLOW + "A fera saiu!");
        sendTitleToAll(ChatColor.YELLOW + "A fera saiu");
        selectNewProcurador();
        giveItemsToEveryOne();
    }
    private void finishGame(boolean hidersWin){
        this.gameState = GameState.STOPING;
        this.time = TIME_TO_CLOSE;
        if(hidersWin){
            sendTitleToAll(ChatColor.GREEN + "Escondedores");
            sendMessageToAll(ChatColor.GREEN + "Escondedores venceram!!!");
        }else{
            sendTitleToAll(ChatColor.RED + "Procuradores");
            sendMessageToAll(ChatColor.RED + "Procuradores venceram!!!");
        }
    }
    public void closeGame(){
        this.gameState = GameState.STOPING;
        Main.sc.cancelTask(runEachSecondId);
        HandlerList.unregisterAll(this.gameListener);
        Main.gameController.remove(this);
        for(Player p : procuradores) {
            p.getInventory().clear();
            p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
            // ESPECIFICO PARA O TESTE, AQUI TROCA PARA SEU LOBBY
            p.teleport(arena.lobby);
        }
        for(Player p : espectadores){
            p.getInventory().clear();
            p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
            // ESPECIFICO PARA O TESTE, AQUI TROCA PARA SEU LOBBY
            p.teleport(arena.lobby);
        }
        for(Player p : escondedores){
            p.getInventory().clear();
            p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
            // ESPECIFICO PARA O TESTE, AQUI TROCA PARA SEU LOBBY
            p.teleport(arena.lobby);
        }
    }

    //player
    private boolean remove(Player player){
        boolean wasPlayer = escondedores.contains(player) || procuradores.contains(player);
        escondedores.remove(player);
        procuradores.remove(player);
        espectadores.remove(player);
        for(PotionEffect potion : player.getActivePotionEffects())
            player.removePotionEffect(potion.getType());
        player.getInventory().clear();
        player.setHealthScale(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        return wasPlayer;
    }
    public void _addEscondedorForced(Player player){
        addPlayer(player, false);
    }
    private void addPlayer(Player player, boolean procurador){
        remove(player);
        player.setGameMode(GameMode.ADVENTURE);
        if(procurador) {
            procuradores.add(player);
            giveProcuradorsItemsTo(player);
        }else {
            escondedores.add(player);
            if(gameState != GameState.RECRUITING)
                giveEscondedoresItemsTo(player);
        }
    }
    private void addEspectador(Player player){
        remove(player);
        player.setGameMode(GameMode.SPECTATOR);
        espectadores.add(player);
    }
    private void selectNewProcurador(){
        if(escondedores.size() > 1){
            Player newProcurador = escondedores.get((new Random()).nextInt(escondedores.size()));
            addPlayer(newProcurador, true);
            sendMessageToAll(ChatColor.GREEN + newProcurador.getName() +  ChatColor.YELLOW + " agora está te procurando!");
        }else
            finishGame(true);
    }
    public void finded(Player player){
        remove(player);
        addPlayer(player, true);
        _verifyIfExistAEscondedor();
    }

    public void join(Player player){
        boolean likePlayer = false;
        if(gameState == GameState.RECRUITING){
            if(this.escondedores.size() < arena.maxPlayers) {
                addPlayer(player, false);
                likePlayer = true;
            }else
                addEspectador(player);
            player.teleport(arena.lobby);
        }else if(gameState != GameState.STOPING){
            addEspectador(player);
            player.teleport(arena.spawn);
        }
        Main.pm.callEvent(new PlayerJoinInGameEvent(this, player, likePlayer));
    }

    public void quit(Player player){
        boolean wasPlayer = remove(player);
        player.getInventory().clear();
        player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
        Main.pm.callEvent(new PlayerQuitOfGameEvent(this, player, wasPlayer));
        _verifyIfExistAProcurador();
    }

    private void _verifyIfExistAProcurador(){
        if(procuradores.size() == 0 && gameState == GameState.GAMEPLAY)
            selectNewProcurador();
    }
    private void _verifyIfExistAEscondedor(){
        if(escondedores.size() == 0 && gameState == GameState.GAMEPLAY)
            finishGame(false);
    }

    //items
    private void giveItemsToEveryOne(){
        _givePowers();
        for(Player p : escondedores)
            giveEscondedoresItemsTo(p);
        for(Player p : procuradores)
            giveProcuradorsItemsTo(p);
    }
    private void _givePowers(){
        for(Player p : escondedores){
            Power power = Main.powersController.getRandomPower(true);
            p.getInventory().addItem(power.getDefaultItemAndAmount());
        }
    }
    private void giveProcuradorsItemsTo(Player p){
        p.getInventory().clear();
        p.getInventory().addItem(GameItems.getStickProcurador());
        p.getInventory().setItem(EquipmentSlot.CHEST, GameItems.getProcuradorRoupa());
        p.getInventory().addItem(GameItems.getFirework(2));
    }
    private void giveEscondedoresItemsTo(Player p){
        p.getInventory().clear();
        p.getInventory().addItem(GameItems.getStickEscondedor());
        p.getInventory().setItem(EquipmentSlot.CHEST, GameItems.getEscondedorRoupa());
    }

    public void _forceTime(int time){
        this.time = time;
    }

    //funcs
    public void sendMessageToAll(String message){
        for(Player p : this.escondedores)
            p.sendMessage(message);
        for(Player p : this.procuradores)
            p.sendMessage(message);
        for(Player p : this.espectadores)
            p.sendMessage(message);
    }

    public void sendTitleToAll(String title){
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"));
        for(Player p : this.escondedores)
            (((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
        for(Player p : this.procuradores)
            (((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
        for(Player p : this.espectadores)
            (((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
    }

}