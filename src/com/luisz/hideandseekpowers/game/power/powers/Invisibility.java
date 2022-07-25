package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Invisibility extends Power {

    public Invisibility(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        this.who.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 8*20, 2));
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 12;
    }
    @Override
    public Material getIcon() {
        return Material.ELYTRA;
    }
    @Override
    public int getDefaultAmount() {
        return 1;
    }
    @Override
    public String getName(){
        return "Invisibility";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.DARK_GRAY;
    }
}