package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FakePower extends Power {

    public FakePower(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        Class<? extends Power> pc = Main.powersController.getRandomPower(true).getClass();
        Power power = Main.powersController.createPower(pc, game, who, whereUse);
        int count = 0;
        while(count < 5 && !game.getGamePowerController().usePower(power)){
            power = Main.powersController.createPower(pc, game, who, whereUse);
            count++;
        }
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
        return Material.SPIDER_EYE;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Fake Power";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.GRAY;
    }
}