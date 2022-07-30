package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;

public class Gift extends Power {

    public Gift(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        Random r = new Random();
        Player pr = game.getEscondedores().get(r.nextInt(game.getEscondedores().size()));
        pr.getInventory().addItem(Main.powersController.getRandomPower(true).getDefaultItemAndAmount());
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
        return Material.DRIED_KELP_BLOCK;
    }
    @Override
    public int getDefaultAmount() {
        return 1;
    }
    @Override
    public boolean thisPowerCanGiveAnother() {
        return true;
    }
    @Override
    public String getName(){
        return "Gift";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.GREEN;
    }
}