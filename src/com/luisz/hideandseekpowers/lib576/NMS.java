package com.luisz.hideandseekpowers.lib576;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class NMS {

    public static void registerWorld(String worldName) {
        for (World world : Bukkit.getServer().getWorlds()) {
            if (world.getName().equalsIgnoreCase(worldName))
                return;
        }
        World w = Bukkit.getServer().createWorld(new WorldCreator(worldName));
        if (w != null) {
            Bukkit.getServer().getWorlds().add(w);
        }
    }

    public static World getWorldByName(String worldName) {
        return Bukkit.getWorld(worldName);
    }

}