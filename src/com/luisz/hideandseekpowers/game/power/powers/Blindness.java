package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blindness extends Power {

    public Blindness(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        for(Player p : game.getProcuradores())
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10*20, 3));
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 20;
    }
    @Override
    public Material getIcon() {
        return Material.ENDER_EYE;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Blindness";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.DARK_GREEN;
    }
}