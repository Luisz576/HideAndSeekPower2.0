package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FakePower extends Power {

    public FakePower(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public boolean onOnlyUse() {
        int count = 0;
        while(count < Main.powersController.size()){
            Power power = Main.powersController.createPower(Main.powersController.get(count), game, who, whereUse);
            if(game.getGamePowerController().usePower(power)) {
                count = Main.powersController.size();
                who.sendMessage(ChatColor.YELLOW + "Você usou " + power.getColorName() + power.getName() + ChatColor.YELLOW + "!");
            }
            count++;
        }
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