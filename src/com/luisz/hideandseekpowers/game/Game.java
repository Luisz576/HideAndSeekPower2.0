package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.game.events.PlayerJoinInGameEvent;
import com.luisz.hideandseekpowers.game.events.PlayerQuitOfGameEvent;
import com.luisz.hideandseekpowers.game.power.GamePowerController;
import com.luisz.hideandseekpowers.game.power.Power;
import com.luisz.hideandseekpowers.game.scoreboard.GameScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game{
    public static final int TIME_TO_START = 30,
            TIME_TO_HIDE = 60,
            TIME_TO_FINISH = 10*60,
            TIME_TO_CLOSE = 20;

    private final GameScoreboard gameScoreboard;
    private final Arena arena;
    public String getArenaName(){
        return arena.name;
    }
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
                break;
            case HIDING:
                if(time <= 0)
                    startGame();
                break;
            case GAMEPLAY:
                if(time <= 0)
                    finishGame(true);
                break;
            case STOPING:
                if(time <= 0)
                    closeGame();
                break;
        }
        time--;
    }

    //STATES
    private void startHiding(){
        this.gameState = GameState.HIDING;
        this.time = TIME_TO_HIDE;
        for(Player p : escondedores)
            p.teleport(arena.spawn);
        for(Player p : espectadores)
            p.teleport(arena.spawn);
        // if something just happens
        for(Player p : procuradores)
            p.teleport(arena.spawn);
    }
    private void startGame(){
        this.gameState = GameState.GAMEPLAY;
        this.time = TIME_TO_FINISH;
        sendMessageToAll(ChatColor.YELLOW + "A fera saiu!");
        sendTitleToAll(ChatColor.YELLOW + "A fera saiu");
        selectNewProcurador();
        giveItemsToEveryOne();
    }
    private void finishGame(boolean hidersWin){
        this.gameState = GameState.STOPING;
        this.time = TIME_TO_CLOSE;
    }
    public void closeGame(){
        this.gameState = GameState.STOPING;
        Main.sc.cancelTask(runEachSecondId);
        HandlerList.unregisterAll(this.gameListener);
        Main.gameController.remove(this);
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
    private void addPlayer(Player player, boolean procurador){
        remove(player);
        player.setGameMode(GameMode.ADVENTURE);
        if(procurador)
            procuradores.add(player);
        else
            escondedores.add(player);
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
        Main.pm.callEvent(new PlayerQuitOfGameEvent(this, player, wasPlayer));
        _verifyIfExistAProcurador();
    }

    private void _verifyIfExistAProcurador(){
        if(procuradores.size() == 0 && gameState == GameState.GAMEPLAY)
            selectNewProcurador();
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
        //todo
    }
    private void giveEscondedoresItemsTo(Player p){
        //todo
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
        //todo
    }

}