package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class SoulExchange extends Power {

    public SoulExchange(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        Random r = new Random();
        List<Player> players = game.getEscondedores();
        players.remove(who);
        Player pt = players.get(r.nextInt(players.size()));
        Location target = pt.getLocation();
        pt.teleport(who.getLocation());
        pt.sendMessage(ChatColor.YELLOW + who.getName() + " trocou com você!");
        who.teleport(target);
        who.sendMessage("Você trocou com " + ChatColor.YELLOW + pt.getName() + "!");
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 30;
    }
    @Override
    public Material getIcon() {
        return Material.DRAGON_BREATH;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Soul Exchange";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.LIGHT_PURPLE;
    }
}