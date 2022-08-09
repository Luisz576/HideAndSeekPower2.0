package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Jump extends Power {

    public Jump(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public boolean onOnlyUse() {
        this.who.setVelocity(new Vector(0, 2, 0));
        return true;
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
        return Material.SLIME_BALL;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Jump";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.GREEN;
    }
}