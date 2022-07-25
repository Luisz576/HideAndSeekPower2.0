package com.luisz.hideandseekpowers.game.arena;

import org.bukkit.Location;
import org.bukkit.World;

public interface IArena {
    String getName();
    Location getSpawn();
    Location getLobby();
    World getWorld();
}