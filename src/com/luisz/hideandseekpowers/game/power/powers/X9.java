package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import com.luisz.hideandseekpowers.lib576.LConvert;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class X9 extends Power {

    public X9(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        List<Player> players = game.getEscondedores();
        players.remove(who);
        if(players.size() > 0){
            //who use
            Invisibility invisibility = new Invisibility(game, who, whereUse);
            Invencible invencible = new Invencible(game, who, whereUse);
            Speed speed = new Speed(game, who, whereUse);
            game.getGamePowerController().usePower(invisibility);
            game.getGamePowerController().usePower(invencible);
            game.getGamePowerController().usePower(speed);
            //random player
            Player nextTo = null;
            List<Player> escondedores = game.getEscondedores();
            escondedores.remove(who);
            for(Player p : escondedores){
                if(nextTo != null){
                    int disNextTo = LConvert.module(who.getLocation().getBlockX() - nextTo.getLocation().getBlockX())
                            + LConvert.module(who.getLocation().getBlockY() - nextTo.getLocation().getBlockY())
                            + LConvert.module(who.getLocation().getBlockZ() - nextTo.getLocation().getBlockZ());
                    int disEsc = LConvert.module(who.getLocation().getBlockX() - p.getLocation().getBlockX())
                            + LConvert.module(who.getLocation().getBlockY() - p.getLocation().getBlockY())
                            + LConvert.module(who.getLocation().getBlockZ() - p.getLocation().getBlockZ());
                    if(disNextTo > disEsc)
                        nextTo = p;
                }else
                    nextTo = p;
            }
            Objects.requireNonNull(nextTo).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 6 * 20, 2));
            game.getWorld().spawnEntity(nextTo.getLocation(), EntityType.FIREWORK);
            game.getWorld().spawnEntity(nextTo.getLocation(), EntityType.FIREWORK);
        }
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