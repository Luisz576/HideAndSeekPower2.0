package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mount extends Power {

    private Horse horse;

    public Mount(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onStart() {
        horse = (Horse) game.getWorld().spawnEntity(whereUse, EntityType.HORSE);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.addPassenger(who);
    }

    @Override
    public void onStop() {
        horse.removePassenger(who);
        horse.remove();
    }

    @Override
    public int getTimeRun() {
        return 10*20;
    }
    @Override
    public int getDelayToUse(){
        return 30;
    }
    @Override
    public Material getIcon() {
        return Material.SADDLE;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Mount";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.YELLOW;
    }
}