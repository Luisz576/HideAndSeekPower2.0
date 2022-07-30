package com.luisz.hideandseekpowers.lib576;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class LConfig {
    private final File file;

    private final YamlConfiguration config;

    public LConfig(@Nonnull String arq, Plugin plugin) {
        this.file = new File(plugin.getDataFolder(), arq + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
        if (!this.file.exists())
            save();
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public File getFile() {
        return this.file;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return this.config.getString(key);
    }

    public int getInt(String key) {
        return this.config.getInt(key);
    }

    public boolean getBool(String key) {
        return this.config.getBoolean(key);
    }

    public List<?> getList(String key) {
        return this.config.getList(key);
    }

    public ItemStack getItem(String key) {
        return this.config.getItemStack(key);
    }

    public List<Map<?, ?>> getMapList(String key) {
        return this.config.getMapList(key);
    }

    public Map<?, ?> getMap(String key) {
        return this.config.getConfigurationSection(key).getValues(false);
    }

    public Location getLocation(String key) {
        String name = this.config.getString(key + ".world");
        NMS.registerWorld(name);
        World w = NMS.getWorldByName(name);
        return new Location(w, this.config
                .getInt(key + ".x"), this.config
                .getInt(key + ".y"), this.config
                .getInt(key + ".z"));
    }

    public Location getLocationWithYaw(String key) {
        String name = this.config.getString(key + ".world");
        NMS.registerWorld(name);
        World w = NMS.getWorldByName(name);
        return new Location(w, this.config
                .getInt(key + ".x"), this.config
                .getInt(key + ".y"), this.config
                .getInt(key + ".z"),
                (float)this.config.getDouble(key + ".yaw"),
                (float)this.config.getDouble(key + ".pitch"));
    }

    public Vector getVector(String key) {
        return new Vector(this.config.getInt(key + ".x"), this.config.getInt(key + ".y"), this.config.getInt(key + ".z"));
    }

    public Boolean hasKey(String key) {
        return Boolean.valueOf(this.config.contains(key));
    }

    public void setValue(String key, String value) {
        this.config.set(key, value);
    }

    public void setValue(String key, int value) {
        this.config.set(key, Integer.valueOf(value));
    }

    public void setValue(String key, boolean value) {
        this.config.set(key, Boolean.valueOf(value));
    }

    public void setValue(String key, List<?> value) {
        this.config.set(key, value);
    }

    public void setValue(String key, ItemStack value) {
        this.config.set(key, value);
    }

    public void setValue(String key, Map<?, ?> value) {
        this.config.set(key, value);
    }

    public void setValue(String key, HashMap<?, ?> value) {
        this.config.set(key, value);
    }

    public void setValue(String key, Location value) {
        this.config.set(key + ".world", value.getWorld().getName() + "");
        this.config.set(key + ".x", Integer.valueOf(value.getBlockX()));
        this.config.set(key + ".y", Integer.valueOf(value.getBlockY()));
        this.config.set(key + ".z", Integer.valueOf(value.getBlockZ()));
    }

    public void setValue(String key, Location value, double yaw, double pitch) {
        this.config.set(key + ".world", value.getWorld().getName() + "");
        this.config.set(key + ".x", Integer.valueOf(value.getBlockX()));
        this.config.set(key + ".y", Integer.valueOf(value.getBlockY()));
        this.config.set(key + ".z", Integer.valueOf(value.getBlockZ()));
        this.config.set(key + ".yaw", Double.valueOf(yaw));
        this.config.set(key + ".pitch", Double.valueOf(pitch));
    }

    public void setValue(String key, Vector value) {
        this.config.set(key + ".x", Integer.valueOf(value.getBlockX()));
        this.config.set(key + ".y", Integer.valueOf(value.getBlockY()));
        this.config.set(key + ".z", Integer.valueOf(value.getBlockZ()));
    }

    public void remove(String key) {
        this.config.set(key, null);
    }
}
