package com.luisz.hideandseekpowers.lib576;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class CustomEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}