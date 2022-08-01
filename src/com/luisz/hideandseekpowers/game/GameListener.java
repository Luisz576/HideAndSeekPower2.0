package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.game.events.PlayerFindedEvent;
import com.luisz.hideandseekpowers.game.events.PlayerJoinInGameEvent;
import com.luisz.hideandseekpowers.game.events.PlayerQuitOfGameEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameListener implements Listener {

    private final Game game;

    public GameListener(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoinInGame(PlayerJoinInGameEvent e){
        if(e.game.getArenaName().equalsIgnoreCase(game.getArenaName())){
            if(e.likePlayer)
                game.sendMessageToAll(ChatColor.GREEN + e.player.getName() + " entrou! " + ChatColor.YELLOW + "[" + game.getAmountOfPlayers() + "/" + game.getMaxPlayers() + "]");
            else
                game.sendMessageToAll(ChatColor.GRAY + e.player.getName() + " está espectando!");
        }
    }

    @EventHandler
    public void onPlayerQuitOfGame(PlayerQuitOfGameEvent e){
        if(e.game.getArenaName().equalsIgnoreCase(game.getArenaName())){
            if(e.wasPlayer)
                game.sendMessageToAll(ChatColor.RED + e.who.getName() + " saiu!");
            else
                game.sendMessageToAll(ChatColor.GRAY + e.who.getName() + " não está mais espectando!");
        }
    }

    @EventHandler
    public void onPlayerIsFindInGame(PlayerFindedEvent e){
        //todo
        switch(e.reason){
            case STICK:
                break;
            case SNOWBALL:
                break;
            case POWER_OF_ANOTHER_PLAYER:
                break;
        }
    }

}