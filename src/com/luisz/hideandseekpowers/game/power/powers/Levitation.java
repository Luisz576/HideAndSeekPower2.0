package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Levitation extends Power {

    public Levitation(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public boolean onOnlyUse() {
        this.who.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 6*20, 3));
        return true;
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 15;
    }
    @Override
    public Material getIcon() {
        return Material.BLAZE_POWDER;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Levitation";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.GRAY;
    }
}