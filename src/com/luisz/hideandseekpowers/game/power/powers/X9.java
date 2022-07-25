package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class X9 extends Power {

    public X9(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        Invisibility invisibility = new Invisibility(game, who, whereUse);
        Invencible invencible = new Invencible(game, who, whereUse);
        game.getGamePowerController().usePower(invisibility);
        game.getGamePowerController().usePower(invencible);
        Random r = new Random();
        List<Player> players = game.getEscondedores();
        players.remove(who);
        Player pt = players.get(r.nextInt(players.size()));
        pt.teleport(game.getProcuradores().get(0).getLocation());
        Invencible invencible2 = new Invencible(game, pt, game.getProcuradores().get(0).getLocation());
        game.getGamePowerController().usePower(invencible2);
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 60;
    }
    @Override
    public Material getIcon() {
        return Material.GHAST_TEAR;
    }
    @Override
    public int getDefaultAmount() {
        return 1;
    }
    @Override
    public String getName(){
        return "X9";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.DARK_RED;
    }
}