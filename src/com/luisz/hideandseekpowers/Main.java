package com.luisz.hideandseekpowers;

import com.luisz.hideandseekpowers.command.Commands;
import com.luisz.hideandseekpowers.controller.GameController;
import com.luisz.hideandseekpowers.controller.PowersController;
import com.luisz.hideandseekpowers.controller.SignController;
import com.luisz.hideandseekpowers.events.Events;
import com.luisz.hideandseekpowers.game.arena.ArenasConfig;
import com.luisz.hideandseekpowers.game.power.powers.*;
import com.luisz.hideandseekpowers.game.sign.SignsConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public class Main extends JavaPlugin{
    public static Main instance;
    public static ConsoleCommandSender cmd;
    public static BukkitScheduler sc;
    public static PluginManager pm;
    public static GameController gameController;
    public static PowersController powersController;
    public static ArenasConfig arenasConfig;
    public static SignsConfig signsConfig;
    public static SignController signController;

    @Override
    public void onEnable() {
        instance = this;
        cmd = Bukkit.getConsoleSender();
        sc = Bukkit.getScheduler();
        pm = Bukkit.getPluginManager();
        arenasConfig = new ArenasConfig(instance);
        signsConfig = new SignsConfig(instance);
        gameController = new GameController();
        signController = new SignController();
        initPowersController();
        signController.loadSignsAndStartRun();
        Objects.requireNonNull(getCommand("hasp")).setExecutor(new Commands());
        pm.registerEvents(new Events(), instance);
        cmd.sendMessage(ChatColor.GREEN + "[HideAndSeekPowers] Ligado!");
    }

    private void initPowersController() {
        powersController = new PowersController();
        powersController.add(Speed.class);
        powersController.add(Invisibility.class);
        powersController.add(Revelation.class);
        powersController.add(Disguise.class);
        powersController.add(Jump.class);
        powersController.add(Mount.class);
        powersController.add(Regive.class);
        powersController.add(FakePower.class);
        powersController.add(Blindness.class);
        powersController.add(Invencible.class);
        powersController.add(Gift.class);
        powersController.add(X9.class);
        powersController.add(Levitation.class);
        powersController.add(SoulExchange.class);
        powersController.add(Hungry.class);
        powersController.add(SecondChance.class);
    }

    @Override
    public void onDisable() {
        cmd.sendMessage(ChatColor.RED + "[HideAndSeekPowers] Desligado!");
    }

}