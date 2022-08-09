package com.luisz.hideandseekpowers.events;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.building.BuildingMemory;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.GameItems;
import com.luisz.hideandseekpowers.game.GameState;
import com.luisz.hideandseekpowers.game.events.PlayerFindedEvent;
import com.luisz.hideandseekpowers.game.events.PlayerJoinInGameEvent;
import com.luisz.hideandseekpowers.game.events.PlayerQuitOfGameEvent;
import com.luisz.hideandseekpowers.game.sign.SignGame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Events implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        if(Main.gameController.get(e.getPlayer()) != null)
            e.setCancelled(true);
        else if(SignGame.isSign(e.getBlock().getType())) {
            if(Main.signController.get(e.getBlock().getLocation()) == null) {
                if (Main.gameController.get(e.getPlayer()) == null) {
                    if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)) {
                        BuildingMemory.buildingSign.put(e.getPlayer(), e.getBlock().getLocation());
                        e.getPlayer().sendMessage(ChatColor.YELLOW + "Editando essa Sign");
                        e.setCancelled(true);
                    }
                }
            }else{
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null){
            Block b = e.getClickedBlock();
            if(SignGame.isSign(b.getType())){
                SignGame s = Main.signController.get(b.getLocation());
                if(s != null) {
                    int res = s.join(e.getPlayer());
                    if(res == 1)
                        e.getPlayer().sendMessage(ChatColor.RED + "A arena está fechada!");
                    else if(res == 2)
                        e.getPlayer().sendMessage(ChatColor.RED + "Você já está em um jogo!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)  {
        if (event.getAction() == Action.PHYSICAL) {
            Block block = event.getClickedBlock();
            if (block == null) return;
            if (block.getType() == Material.FARMLAND || block.getType() == Material.LEGACY_SOIL) {
                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                event.setCancelled(true);
                block.setBlockData(block.getBlockData());
            }
        }
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent e){
        if(Main.gameController.get(e.getPlayer()) != null) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Você não pode dormir durante uma partida!");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Game game = Main.gameController.get(e.getPlayer());
        if(game != null)
            game.quit(e.getPlayer());
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        if(Main.gameController.get(e.getPlayer()) != null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onBreakBlocohanging(HangingBreakEvent e) {
        for(int i = 0; i < Main.gameController.size(); i++)
            if(e.getEntity().getWorld().getName().equalsIgnoreCase(Main.gameController.get(i).getWorld().getName())) {
                e.setCancelled(true);
                return;
            }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        if (Main.gameController.get(e.getPlayer()) != null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player) {
            if (Main.gameController.get((Player) e.getWhoClicked()) != null)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorManipulate(PlayerArmorStandManipulateEvent e){
        if(Main.gameController.get(e.getPlayer()) != null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityInteractAtEntity(PlayerInteractAtEntityEvent e){
        Game game = Main.gameController.get(e.getPlayer());
        if(game != null){
            haveUsedPower(game, e.getPlayer());
        }
    }
    @EventHandler
    public void onEntityInteract(PlayerInteractEvent e){
        Game game = Main.gameController.get(e.getPlayer());
        if(game != null){
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
                haveUsedPower(game, e.getPlayer());
        }
    }
    private void haveUsedPower(Game game, Player player){
        ItemStack item = player.getInventory().getItemInMainHand();
        if(Main.powersController.isAPower(item)){
            if(game.getGamePowerController().usePower(Main.powersController.createPower(Main.powersController.getPowerClass(item), game, player, player.getLocation()))){
                //todo
            }
        }else if(GameItems.isAProcuradorPower(item)){
            if(item.getType().equals(Material.FIREWORK_ROCKET)){
                for(Player p : game.getEscondedores()) {
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                }
            }else if(item.getType().equals(Material.GLOWSTONE_DUST)){
                for(Player p : game.getEscondedores())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 8*20, 1));
            }
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e){
        if(e.getEntity() instanceof Player)
            if(Main.gameController.get((Player) e.getEntity()) != null)
                e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player)
            if(Main.gameController.get((Player) e.getEntity()) != null)
                if(e.getCause() == EntityDamageEvent.DamageCause.FALL ||
                    e.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                    e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                    e.getCause() == EntityDamageEvent.DamageCause.LAVA ||
                    e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    e.setCancelled(true);
                }else if(e.getCause() == EntityDamageEvent.DamageCause.VOID){
                    Game game = Main.gameController.get((Player) e.getEntity());
                    if(game.getGameState() == GameState.RECRUITING)
                        ((Player) e.getEntity()).teleport(game.getLobby());
                    else
                        ((Player) e.getEntity()).teleport(game.getSpawn());
                }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        if(Main.gameController.get(e.getPlayer()) != null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onPickupArrow(PlayerPickupItemEvent e){
        if(Main.gameController.get(e.getPlayer()) != null)
            e.setCancelled(true);
    }

    @EventHandler
    public void onSnowBallDamage(ProjectileHitEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Snowball &&
                e.getHitEntity() instanceof Player) {
            Player p = (Player)e.getHitEntity();
            if (e.getEntity().getShooter() instanceof Player) {
                Player shooter = (Player)e.getEntity().getShooter();
                if(Main.gameController.isInSame(shooter, p)){
                    Game game = Main.gameController.get(p);
                    if(game.getEscondedores().contains(p)
                        && !game.playersThatCantKill.contains(p)){
                        Main.pm.callEvent(new PlayerFindedEvent(game, p, shooter));
                    }
                }
            }
        }
    }

    //

    @EventHandler
    public void onPlayerJoinInGame(PlayerJoinInGameEvent e){
        if(e.likePlayer)
            e.game.sendMessageToAll(ChatColor.GREEN + e.player.getName() + " entrou! " + ChatColor.YELLOW + "[" + e.game.getAmountOfPlayers() + "/" + e.game.getMaxPlayers() + "]");
        else
            e.game.sendMessageToAll(ChatColor.GRAY + e.player.getName() + " está espectando!");
    }

    @EventHandler
    public void onPlayerQuitOfGame(PlayerQuitOfGameEvent e){
        if(e.wasPlayer)
            e.game.sendMessageToAll(ChatColor.RED + e.who.getName() + " saiu!");
        else
            e.game.sendMessageToAll(ChatColor.GRAY + e.who.getName() + " não está mais espectando!");
    }

    @EventHandler
    public void onPlayerIsFindInGame(PlayerFindedEvent e){
        e.game.addPointTo(e.finder);
        e.game.finded(e.who);
        e.who.setHealth(20);
        e.who.setFoodLevel(20);
        e.who.setLevel(0);
        for(PotionEffect pe : e.who.getActivePotionEffects())
            e.who.removePotionEffect(pe.getType());
        e.finder.getInventory().addItem(GameItems.getFirework(2));
        e.game.sendMessageToAll(ChatColor.GREEN + e.who.getName() + ChatColor.YELLOW + " foi encontrado por " + ChatColor.RED + e.finder.getName() + ChatColor.YELLOW + "!");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Game gameOfDamager = Main.gameController.get(damager);
            if (gameOfDamager != null) {
                e.setCancelled(true);
                if (e.getEntity() instanceof Player) {
                    Player entity = (Player) e.getEntity();
                    Game gameOfEntity = Main.gameController.get(entity);
                    if (gameOfEntity != null && gameOfEntity.getArenaName().equalsIgnoreCase(gameOfDamager.getArenaName())) {
                        if (gameOfEntity.getProcuradores().contains(damager) && gameOfEntity.getEscondedores().contains(entity)) {
                            if (gameOfEntity.getGameState() == GameState.GAMEPLAY) {
                                e.setDamage(10.0);
                                if (entity.getHealth() - 10.0 > 0.0) {
                                    e.setCancelled(false);
                                } else {
                                    Main.pm.callEvent(new PlayerFindedEvent(gameOfEntity, entity, damager));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}