package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Hungry extends Power {

    public Hungry(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void run() {
        for(Player p : game.getProcuradores())
            p.setFoodLevel(2);
    }

    @Override
    public void onStop() {
        for(Player p : game.getProcuradores())
            p.setFoodLevel(20);
    }

    @Override
    public int getTimeRun() {
        return 10*20;
    }
    @Override
    public int getDelayToUse(){
        return 15;
    }
    @Override
    public Material getIcon() {
        return Material.COOKED_BEEF;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Hungry";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.YELLOW;
    }
}