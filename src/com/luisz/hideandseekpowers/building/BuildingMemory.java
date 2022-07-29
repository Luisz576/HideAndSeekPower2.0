package com.luisz.hideandseekpowers.building;

import com.luisz.hideandseekpowers.game.arena.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BuildingMemory {

    public static final HashMap<Player, Arena> buildingArena = new HashMap<>();
    public static final HashMap<Player, Location> buildingSign = new HashMap<>();

}