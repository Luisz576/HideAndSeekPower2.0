package com.luisz.hideandseekpowers.game.arena;

import org.bukkit.Location;
import org.bukkit.World;

public class Arena implements IArena{
    private final String name;
    private final Location spawn, lobby;

    public Arena(String name, Location spawn, Location lobby){
        this.name = name;
        this.spawn = spawn;
        this.lobby = lobby;
    }

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public Location getSpawn() {
        return this.spawn;
    }
    @Override
    public Location getLobby() {
        return this.lobby;
    }
    @Override
    public World getWorld() {
        return this.spawn.getWorld();
    }
}