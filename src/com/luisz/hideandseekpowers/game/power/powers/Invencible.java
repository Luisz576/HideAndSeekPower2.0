package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Invencible extends Power {

    public Invencible(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void run() {
        this.who.setHealth(20);
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
        return Material.TOTEM_OF_UNDYING;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Invencible";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.GOLD;
    }
}