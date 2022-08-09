package com.luisz.hideandseekpowers.game;

import com.luisz.hideandseekpowers.lib576.LConvert;
import com.luisz.hideandseekpowers.lib576.Packets;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HeartProcurador {

    public static final int MAX_FRAMES = 5, MAX_DIS = 20;

    private final Game game;
    private final Player p;

    private boolean rendering = true, growing = true;
    private int currentFrame = 0;

    public HeartProcurador(Game game, Player p){
        this.p = p;
        this.game = game;
    }

    private String getDraw(){
        StringBuilder a = new StringBuilder(), b = new StringBuilder();
        for(int i = 0; i < currentFrame; i++) {
            a.append("(");
            b.append(")");
        }
        return ChatColor.RED + "" + a + " ♡ " + b;
    }

    public void update(){
        if(hasSomePlayerNext()){
            if(rendering){
                Packets.sendActionTitle(this.p, getDraw());
                if(growing){
                    if(currentFrame >= MAX_FRAMES)
                        growing = false;
                    else
                        currentFrame++;
                }else{
                    if(currentFrame > 0)
                        currentFrame--;
                    else
                        growing = true;
                }
            }
            rendering = true;
        }else{
            if(rendering)
                Packets.sendActionTitle(this.p, "");
            rendering = false;
            growing = false;
            currentFrame = 0;
        }
    }

    private boolean hasSomePlayerNext(){
        for(Player e : game.getEscondedores()){
            int dis = LConvert.module(e.getLocation().getBlockX() - p.getLocation().getBlockX())
                    + LConvert.module(e.getLocation().getBlockY() - p.getLocation().getBlockY())
                    + LConvert.module(e.getLocation().getBlockZ() - p.getLocation().getBlockZ());
            if(dis <= MAX_DIS)
                return true;
        }
        return false;
    }

}