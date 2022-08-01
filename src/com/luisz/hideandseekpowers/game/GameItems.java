package com.luisz.hideandseekpowers.game;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class GameItems {

    public static ItemStack getStickEscondedor(){
        ItemStack stick = createItem(Material.STICK, 1, ChatColor.YELLOW + "Sai fi");
        stick.addEnchantment(Enchantment.KNOCKBACK, 1);
        return stick;
    }
    public static ItemStack getEscondedorRoupa(){
        return createItem(Material.LEATHER_CHESTPLATE, 1, ChatColor.DARK_PURPLE + "Camuflagem");
    }
    public static ItemStack getStickProcurador(){
        return createItem(Material.BLAZE_POWDER, 1, ChatColor.RED + "C é besta");
    }
    public static ItemStack getProcuradorRoupa(){
        return createItem(Material.IRON_CHESTPLATE, 1, ChatColor.DARK_PURPLE + "Fera");
    }
    public static ItemStack getFirework(int amount){
        return createItem(Material.FIREWORK_ROCKET, amount, ChatColor.YELLOW + "Soltem fogos");
    }
    public static ItemStack getSnowball(int amount){
        return createItem(Material.SNOWBALL, amount, ChatColor.BLUE + "Tiro");
    }
    public static ItemStack getGlowstoneDust(int amount){
        return createItem(Material.GLOWSTONE_DUST, amount, ChatColor.GOLD + "Brilhem");
    }

    private static ItemStack createItem(Material material, int amount, String name){
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}