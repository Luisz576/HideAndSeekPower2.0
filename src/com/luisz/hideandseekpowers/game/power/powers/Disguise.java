package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Disguise extends Power {

    private Chicken chicken;

    public Disguise(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onStart() {
        chicken = (Chicken) game.getWorld().spawnEntity(whereUse, EntityType.CHICKEN);
        who.setGameMode(GameMode.SPECTATOR);
        who.teleport(chicken.getLocation());
    }

    @Override
    public void onStop() {
        who.teleport(chicken.getLocation().add(0, 1, 0));
        who.setGameMode(GameMode.ADVENTURE);
        chicken.remove();
        chicken.setHealth(0);
    }

    @Override
    public void run() {
        who.teleport(chicken.getLocation());
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
        return Material.DIAMOND;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Disguise";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.BLUE;
    }
}