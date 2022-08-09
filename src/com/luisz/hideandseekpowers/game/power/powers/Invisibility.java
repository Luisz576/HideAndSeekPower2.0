package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.GameItems;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Invisibility extends Power {

    private final int DURATION;

    public Invisibility(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
        this.DURATION = 8*20;
    }
    public Invisibility(Game game, Player who, Location whereUse, int forcedTime){
        super(game, who, whereUse);
        this.DURATION = forcedTime;
    }

    @Override
    public void onStart() {
        this.who.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, DURATION, 2));
        this.who.getInventory().setItem(EquipmentSlot.CHEST, null);
    }

    @Override
    public void onStop() {
        if(game.isEscondedor(this.who))
            this.who.getInventory().setItem(EquipmentSlot.CHEST, GameItems.getEscondedorRoupa());
    }

    @Override
    public int getTimeRun() {
        return DURATION;
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
        return 2;
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