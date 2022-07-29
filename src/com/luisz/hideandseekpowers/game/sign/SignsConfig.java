package com.luisz.hideandseekpowers.game.sign;

import com.luisz.hideandseekpowers.game.arena.Arena;
import com.luisz.hideandseekpowers.lib576.LConfig;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SignsConfig {
    private LConfig config;
    private final List<SignGame> _signs = new ArrayList<>();

    public SignsConfig(Plugin plugin){
        config = new LConfig("signs", plugin);
        config.save();
        _loadSigns();
    }

    private void _loadSigns(){
        List<String> signs = (List<String>) config.getList("signs_names");
        if(signs != null)
            for(String sign : signs){
                String a = config.getString("signs." + sign + ".arena");
                Location l = config.getLocation("signs." + sign + ".loc");
                _signs.add(new SignGame(sign, l, a));
            }
        else{
            config.setValue("signs_names", new ArrayList<String>());
            config.save();
        }
    }

    public void addSign(SignGame signGame){
        if(getSign(signGame.signName) == null){
            List<String> signs = (List<String>) config.getList("signs_names");
            signs.add(signGame.arenaName);
            String path = "signs." + signGame.signName;
            config.setValue(path + ".arena", signGame.arenaName);
            config.setValue(path + ".loc", signGame.location);
            _signs.add(signGame);
            save();
        }
    }

    public int removeSign(String signName){
        if(getSign(signName) != null){
            config.remove("signs." + signName);
            _signs.remove(getSign(signName));
            save();
            return 0;
        }
        return 1;
    }

    private void save(){
        List<String> signs = new ArrayList<>();
        for(SignGame a : _signs)
            signs.add(a.signName);
        config.setValue("signs_names", signs);
        config.save();
    }

    public SignGame getSign(String signName){
        for(SignGame s : _signs)
            if(s.signName.equalsIgnoreCase(signName))
                return s;
        return null;
    }

    public List<SignGame> getAll(){
        return new ArrayList<>(this._signs);
    }

}