package com.luisz.hideandseekpowers.lib576;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class Packets {

    public static void sendActionTitle(Player who, String title){
        who.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(title));
    }

}