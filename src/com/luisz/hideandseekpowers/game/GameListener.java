package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.events.PlayerFindedEvent;
import com.luisz.hideandseekpowers.game.events.PlayerJoinInGameEvent;
import com.luisz.hideandseekpowers.game.events.PlayerQuitOfGameEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
        game.addPointTo(e.finder);
        game.finded(e.who);
        game.sendMessageToAll(ChatColor.GREEN + e.who.getName() + ChatColor.YELLOW + " foi encontrado por " + ChatColor.RED + e.finder.getName() + ChatColor.YELLOW + "!");
    }

    //
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player damager = (Player) e.getDamager();
            Game gameOfDamager = Main.gameController.get(damager);
            if(gameOfDamager != null && gameOfDamager.getArenaName().equalsIgnoreCase(game.getArenaName())){
                e.setCancelled(true);
                if(e.getEntity() instanceof Player){
                    Player entity = (Player) e.getEntity();
                    Game gameOfEntity = Main.gameController.get(entity);
                    if(gameOfEntity != null && gameOfEntity.getArenaName().equalsIgnoreCase(game.getArenaName())){
                        if(game.getProcuradores().contains(damager) && game.getEscondedores().contains(entity)) {
                            if (game.getGameState() == GameState.GAMEPLAY) {
                                e.setDamage(10.0);
                                if(entity.getHealth() - 10.0 > 0.0){
                                    e.setCancelled(false);
                                }else{
                                    Main.pm.callEvent(new PlayerFindedEvent(game, entity, damager));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityInteractAtEntity(PlayerInteractAtEntityEvent e){
        //todo: powers & procuradores powers
    }
    @EventHandler
    public void onEntityInteractAtEntity(PlayerInteractEvent e){
        //todo
    }

}