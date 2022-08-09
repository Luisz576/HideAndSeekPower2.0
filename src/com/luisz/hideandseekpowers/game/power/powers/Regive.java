package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Regive extends Power {

    public Regive(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public boolean onOnlyUse() {
        List<Player> escondedores = game.getEscondedores();
        escondedores.remove(who);
        for(Player p : escondedores){
            for(int i = 0; i < 36; i++){
                if(Main.powersController.isAPower(p.getInventory().getItem(i))){
                    p.getInventory().setItem(i, Main.powersController.getRandomPower(false).getDefaultItemAndAmount());
                }
            }
        }
        return true;
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
    public boolean thisPowerCanGiveAnother() {
        return true;
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