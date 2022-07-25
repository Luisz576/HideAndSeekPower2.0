package com.luisz.hideandseekpowers.game.arena;

import com.luisz.hideandseekpowers.lib576.LConfig;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ArenasConfig {

    private final LConfig config;
    private final List<Arena> _arenas = new ArrayList<>();

    public ArenasConfig(Plugin plugin){
        config = new LConfig("arenas", plugin);
        config.save();
        _loadArenas();
    }

    private void _loadArenas(){
        List<String> arenas = (List<String>) config.getList("arenas_names");
        for(String arena : arenas){
            Arena a = new Arena();
            a.name = arena;
            a.maxPlayers = config.getInt("arenas." + arena + ".maxp");
            a.minPlayers = config.getInt("arenas." + arena + ".minp");
            a.lobby = config.getLocation("arenas" + arena + "lobby");
            a.spawn = config.getLocation("arenas" + arena + "spawn");
            _arenas.add(a);
        }
    }

    public Arena getArena(String arena){
        for(Arena a : _arenas){
            if(a.name.equalsIgnoreCase(arena))
                return a;
        }
        return null;
    }

}