package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;

public class SecondChance extends Power {

    public SecondChance(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public boolean onOnlyUse() {
        if(game.getProcuradores().size() > 1){
            Player p = game.getProcuradores().get(new Random().nextInt(game.getProcuradores().size()));
            game._addEscondedorForced(p);
            game.sendMessageToAll(ChatColor.GREEN + p.getName() + ChatColor.YELLOW + " recebeu uma segunda chance de " + ChatColor.RED + who.getName() + ChatColor.YELLOW + "!");
        }else
            who.sendMessage(ChatColor.RED + "Só há uma fera!");
        return false;
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
        return Material.ROSE_BUSH;
    }
    @Override
    public int getDefaultAmount() {
        return 1;
    }
    @Override
    public boolean thisPowerCanGiveAnother() {
        return false;
    }
    @Override
    public String getName(){
        return "Second Chance";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.DARK_RED;
    }
}