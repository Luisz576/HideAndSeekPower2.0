package com.luisz.hideandseekpowers.lib576;

import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS {

    public static void sendTitle(String title, Player who){
        (((CraftPlayer) who).getHandle()).playerConnection.sendPacket(new PacketPlayOutChat((IChatBaseComponent) IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}"), ChatMessageType.GAME_INFO, who.getUniqueId()));
    }

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