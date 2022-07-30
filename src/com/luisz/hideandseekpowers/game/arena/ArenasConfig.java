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
        if(arenas != null)
            for(String arena : arenas){
                Arena a = new Arena();
                a.name = arena;
                a.maxPlayers = config.getInt("arenas." + arena + ".maxp");
                a.minPlayers = config.getInt("arenas." + arena + ".minp");
                a.lobby = config.getLocation("arenas." + arena + ".lobby");
                a.spawn = config.getLocation("arenas." + arena + ".spawn");
                _arenas.add(a);
            }
        else{
            config.setValue("arenas_names", new ArrayList<String>());
            config.save();
        }
    }

    public List<Arena> getAllArenas(){
        return new ArrayList<>(this._arenas);
    }

    public int addArena(Arena arena){
        if(getArena(arena.name) == null){
            List<String> arenas = (List<String>) config.getList("arenas_names");
            arenas.add(arena.name);
            String path = "arenas." + arena.name;
            config.setValue(path + ".maxp", arena.maxPlayers);
            config.setValue(path + ".minp", arena.minPlayers);
            config.setValue(path + ".lobby", arena.lobby);
            config.setValue(path + ".spawn", arena.spawn);
            _arenas.add(arena);
            save();
            return 0;
        }
        return 1;
    }

    public int removeArena(String arenaName){
        if(getArena(arenaName) != null){
            config.remove("arenas." + arenaName);
            _arenas.remove(getArena(arenaName));
            save();
            return 0;
        }
        return 1;
    }

    private void save(){
        List<String> arenas = new ArrayList<>();
        for(Arena a : _arenas)
            arenas.add(a.name);
        config.setValue("arenas_names", arenas);
        config.save();
    }

    public Arena getArena(String arena){
        for(Arena a : _arenas){
            if(a.name.equalsIgnoreCase(arena))
                return a;
        }
        return null;
    }

}