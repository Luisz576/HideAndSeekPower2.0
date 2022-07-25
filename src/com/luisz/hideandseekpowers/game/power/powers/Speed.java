package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Speed extends Power {

    public Speed(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        this.who.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 8*20, 2));
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
        return Material.RABBIT_FOOT;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Speed";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.YELLOW;
    }
}