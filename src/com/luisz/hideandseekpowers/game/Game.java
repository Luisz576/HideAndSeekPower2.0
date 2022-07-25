package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.game.power.GamePowerController;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame{
    public static final int TIME_TO_START = 30,
            TIME_TO_HIDE = 60,
            TIME_TO_FINISH = 10*60,
            TIME_TO_CLOSE = 20,
            MAX_PLAYERS = 10;

    private final String id;
    @Override
    public String getId() {
        return this.id;
    }
    private final Arena arena;
    @Override
    public String getArenaName(){
        return arena.getName();
    }
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

    private final List<Player> players = new ArrayList<>(),
            espectadores = new ArrayList<>();

    private final int runEachTickId;
    private final int runEachSecondId;
    private int time;

    @Override
    public boolean isPlayerInsideThisGame(Player player) {
        return this.players.contains(player) || this.espectadores.contains(player);
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

    //STATES
    private void startHiding(){
        this.gameState = GameState.HIDING;
        this.time = TIME_TO_HIDE;
    }
    private void startGame(){
        this.gameState = GameState.GAMEPLAY;
        this.time = TIME_TO_FINISH;
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
        Main.gameController.remove(this);
    }

    //player
    private void remove(Player player){
        players.remove(player);
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
    private void addPlayer(Player player){
        remove(player);
        player.setGameMode(GameMode.ADVENTURE);
        players.add(player);
    }
    private void addEspectador(Player player){
        remove(player);
        player.setGameMode(GameMode.SPECTATOR);
        espectadores.add(player);
    }

    public boolean join(Player player){
        if(gameState == GameState.RECRUITING){
            if(this.players.size() < MAX_PLAYERS)
                addPlayer(player);
            else
                addEspectador(player);
            player.teleport(arena.getLobby());
        }else if(gameState != GameState.STOPING){
            addEspectador(player);
            player.teleport(arena.getSpawn());
        }else
            return false;
        return true;
    }

    public void quit(Player player){
        remove(player);
    }

}