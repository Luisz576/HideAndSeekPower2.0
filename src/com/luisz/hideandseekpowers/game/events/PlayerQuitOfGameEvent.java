package com.luisz.hideandseekpowers.game.events;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.lib576.CustomEvent;
import org.bukkit.entity.Player;

public class PlayerQuitOfGameEvent extends CustomEvent {

    public final Game game;
    public final Player who;
    public final boolean wasPlayer;

    public PlayerQuitOfGameEvent(Game game, Player who, boolean wasPlayer){
        this.game = game;
        this.who = who;
        this.wasPlayer = wasPlayer;
    }

}
