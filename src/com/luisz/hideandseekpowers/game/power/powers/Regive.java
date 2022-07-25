package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Regive extends Power {

    public Regive(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        this.game._givePowers();
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 0;
    }
    @Override
    public Material getIcon() {
        return Material.NETHER_STAR;
    }
    @Override
    public int getDefaultAmount() {
        return 1;
    }
    @Override
    public String getName(){
        return "Regive";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.WHITE;
    }
}