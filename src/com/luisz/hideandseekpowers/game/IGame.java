package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.game.power.GamePowerController;
import org.bukkit.entity.Player;

public interface IGame {
    String getId();
    String getArenaName();
    GameState getGameState();
    GamePowerController getGamePowerController();
    boolean isPlayerInsideThisGame(Player player);
    void closeGame();
}