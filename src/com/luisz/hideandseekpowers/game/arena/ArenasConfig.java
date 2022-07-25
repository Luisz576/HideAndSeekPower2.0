package com.luisz.hideandseekpowers.game.arena;

import com.luisz.hideandseekpowers.lib576.LConfig;
import org.bukkit.plugin.Plugin;

public class ArenasConfig {

    private LConfig config;

    public ArenasConfig(Plugin plugin){
        config = new LConfig("arenas", plugin);
        //todo load
    }

}