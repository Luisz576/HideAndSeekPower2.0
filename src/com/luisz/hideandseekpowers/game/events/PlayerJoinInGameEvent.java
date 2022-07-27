package com.luisz.hideandseekpowers.game.events;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.lib576.CustomEvent;
import org.bukkit.entity.Player;

public class PlayerJoinInGameEvent extends CustomEvent {

    public final Game game;
    public final Player player;
    public final boolean likePlayer;

    public PlayerJoinInGameEvent(Game game, Player player, boolean likePlayer){
        this.game = game;
        this.player = player;
        this.likePlayer = likePlayer;
    }

}