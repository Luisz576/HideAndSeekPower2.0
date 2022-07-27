package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.game.power.GamePowerController;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame{
    public static final int TIME_TO_START = 30,
            TIME_TO_HIDE = 60,
            TIME_TO_FINISH = 10*60,
            TIME_TO_CLOSE = 20;

    private final String id;
    @Override
    public String getId() {
        return this.id;
    }
    private final Arena arena;
    @Override
    public String getArenaName(){
        return arena.name;
    }
    public World getWorld(){ return arena.getWorld(); }
    private final GamePowerController gamePowerController;
    @Override
    public GamePowerController getGamePowerController() {
        return this.gamePowerController;
    }
    private GameState gameState;
    @Override
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

    private final int runEachTickId;
    private final int runEachSecondId;
    private int time;

    @Override
    public boolean isPlayerInsideThisGame(Player player) {
        return this.procuradores.contains(player) || this.escondedores.contains(player) || this.espectadores.contains(player);
    }

    public Game(String id, Arena arena){
        this.id = id;
        this.arena = arena;
        this.gameState = GameState.RECRUITING;
        this.time = TIME_TO_START;
        this.gamePowerController = new GamePowerController();
        this.runEachTickId = Main.sc.scheduleSyncRepeatingTask(Main.instance, this::runEachTick, 0, 1L);
        this.runEachSecondId = Main.sc.scheduleSyncRepeatingTask(Main.instance, this::runEachSecond, 0, 20L);
        Main.gameController.add(this);
        this.arena.getWorld().setDifficulty(Difficulty.PEACEFUL);
        this.gameListener = new GameListener(this);
        Main.pm.registerEvents(this.gameListener, Main.instance);
    }

    private void runEachTick(){

    }
    private void runEachSecond(){
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
                    finishGame();
                break;
            case STOPING:
                if(time <= 0)
                    closeGame();
                break;
        }
        time--;
    }

    //Items
    public void _givePowers(boolean canGiveOneThatCanGiveAnother){
        for(Player p : escondedores){
            p.getInventory().clear();
            Power power = Main.powersController.getRandomPower(canGiveOneThatCanGiveAnother);
            //todo give power
        }
    }

    //STATES
    private void startHiding(){
        this.gameState = GameState.HIDING;
        this.time = TIME_TO_HIDE;
        for(Player p : escondedores)
            p.teleport(arena.spawn);
        for(Player p : espectadores)
            p.teleport(arena.spawn);
    }
    private void startGame(){
        this.gameState = GameState.GAMEPLAY;
        this.time = TIME_TO_FINISH;
        //todo selecionar procuradores
        _givePowers(true);
    }
    private void finishGame(){
        this.gameState = GameState.STOPING;
        this.time = TIME_TO_CLOSE;
    }
    @Override
    public void closeGame(){
        this.gameState = GameState.STOPING;
        Main.sc.cancelTask(runEachTickId);
        Main.sc.cancelTask(runEachSecondId);
        HandlerList.unregisterAll(this.gameListener);
        Main.gameController.remove(this);
    }

    //player
    private void remove(Player player){
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

    public boolean join(Player player){
        if(gameState == GameState.RECRUITING){
            if(this.escondedores.size() < arena.maxPlayers)
                addPlayer(player, false);
            else
                addEspectador(player);
            player.teleport(arena.lobby);
        }else if(gameState != GameState.STOPING){
            addEspectador(player);
            player.teleport(arena.spawn);
        }else
            return false;
        return true;
    }

    public void quit(Player player){
        remove(player);
    }

}