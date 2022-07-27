package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.game.events.PlayerJoinInGameEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameListener implements Listener {

    private final Game game;

    public GameListener(Game game){
        this.game = game;
    }

    //todo

    @EventHandler
    public void onPlayerJoinInGame(PlayerJoinInGameEvent e){
        //todo
    }

}