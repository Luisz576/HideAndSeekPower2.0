package com.luisz.hideandseekpowers.events;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.building.BuildingMemory;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.GameItems;
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
import org.bukkit.event.inventory.InventoryInteractEvent;
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
                    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_PICKAXE) {
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
    public void onInventoryInteract(InventoryInteractEvent e){
        if(e.getWhoClicked() instanceof Player)
            if(Main.gameController.get((Player) e.getWhoClicked()) != null)
                e.setCancelled(true);
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
            e.setCancelled(haveUsedPower(game, e.getPlayer()));
        }
    }
    @EventHandler
    public void onEntityInteract(PlayerInteractEvent e){
        Game game = Main.gameController.get(e.getPlayer());
        if(game != null){
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
                e.setCancelled(haveUsedPower(game, e.getPlayer()));
        }
    }
    private boolean haveUsedPower(Game game, Player player){
        boolean setCancelled = true;
        ItemStack item = player.getInventory().getItemInMainHand();
        if(Main.powersController.isAPower(item)){
            if(game.getGamePowerController().usePower(Main.powersController.createPower(Main.powersController.getPowerClass(item), game, player, player.getLocation()))){
                if(item.getAmount() <= 1)
                    player.getInventory().setItemInMainHand(null);
                else{
                    item.setAmount(item.getAmount() - 1);
                    player.getInventory().setItemInMainHand(item);
                }
            }
        }else if(GameItems.isAProcuradorPower(item)){
            if(item.getAmount() <= 1)
                player.getInventory().setItemInMainHand(null);
            else{
                item.setAmount(item.getAmount() - 1);
                player.getInventory().setItemInMainHand(item);
            }
            if(item.getType() == Material.FIREWORK_ROCKET){
                for(Player p : game.getEscondedores()) {
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                }
            }else if(item.getType() == Material.SNOWBALL)
                setCancelled = false;
            else if(item.getType() == Material.GLOWSTONE_DUST){
                for(Player p : game.getEscondedores())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 8*20, 1));
            }
        }
        return setCancelled;
    }

}