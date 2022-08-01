package com.luisz.hideandseekpowers.game.events;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.lib576.CustomEvent;
import org.bukkit.entity.Player;

public class PlayerFindedEvent extends CustomEvent {

    public final Game game;
    public final Player who, finder;

    public PlayerFindedEvent(Game game, Player who, Player finder){
        this.game = game;
        this.who = who;
        this.finder = finder;
    }

}