package com.luisz.hideandseekpowers.game.power;

import com.luisz.hideandseekpowers.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public abstract class Power implements IPower{
    protected final Game game;
    protected final Player who;
    @Override
    public final Player getWhoUse(){
        return this.who;
    }
    protected final Location whereUse;

    public Power(Game game, Player who, Location whereUse){
        this.game = game;
        this.who = who;
        this.whereUse = whereUse;
    }

    @Override
    public void onStart() {}
    @Override
    public void onStop() {}
    @Override
    public boolean onOnlyUse() {
        return false;
    }
    @Override
    public void run() {}
    @Override
    public boolean thisPowerCanGiveAnother(){
        return false;
    }
    @Override
    public void onNeedWaitDelay(int delay) {
        int delayWait = delay / 20 + (delay % 20 == 0 ? 0 : 1);
        who.sendMessage(ChatColor.RED + "Espere " + delayWait + " segundo(s) para usar " + getColorName() + getName() + ChatColor.RED + " novamente!");
    }

    public ItemStack getDefaultItemAndAmount(){
        ItemStack item = new ItemStack(getIcon(), getDefaultAmount());
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(getColorName() + getName());
        item.setItemMeta(meta);
        return item;
    }

    public abstract Material getIcon();
    public abstract int getDefaultAmount();
    public abstract String getName();
    public abstract ChatColor getColorName();
}