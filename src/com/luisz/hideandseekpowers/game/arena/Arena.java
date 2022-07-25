package com.luisz.hideandseekpowers.game.arena;

import org.bukkit.Location;
import org.bukkit.World;

public class Arena{
    public String name = "";
    public Location spawn = null, lobby = null;
    public int maxPlayers = 2, minPlayers = 2;
    public World getWorld(){
        return spawn != null ? spawn.getWorld() : null;
    }
}