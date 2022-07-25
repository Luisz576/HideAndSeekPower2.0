package com.luisz.hideandseekpowers.game.power.powers;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import com.luisz.hideandseekpowers.lib576.NMS;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Revelation extends Power {

    public Revelation(Game game, Player who, Location whereUse){
        super(game, who, whereUse);
    }

    @Override
    public void onOnlyUse() {
        for(Player procurador : game.getProcuradores()) {
            procurador.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 6 * 20, 2));
            NMS.sendTitle(ChatColor.RED + "Revelado", procurador);
        }
    }

    @Override
    public int getTimeRun() {
        return 0;
    }
    @Override
    public int getDelayToUse(){
        return 12;
    }
    @Override
    public Material getIcon() {
        return Material.REDSTONE_TORCH;
    }
    @Override
    public int getDefaultAmount() {
        return 2;
    }
    @Override
    public String getName(){
        return "Revelation";
    }
    @Override
    public ChatColor getColorName(){
        return ChatColor.RED;
    }
}