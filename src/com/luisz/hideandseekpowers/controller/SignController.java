package com.luisz.hideandseekpowers.controller;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.sign.SignGame;
import org.bukkit.Location;

public class SignController extends Controller<SignGame>{

    public void loadSignsAndStartRun(){
        this._data.clear();
        this._data.addAll(Main.signsConfig.getAll());
        Main.sc.scheduleSyncRepeatingTask(Main.instance, () -> {
            for(SignGame s : this._data)
                s._update();
        }, 0, 5L);
    }

    public SignGame get(Location location){
        for(SignGame s : _data)
            if(s.location.getBlockX() == location.getBlockX() &&
                s.location.getBlockY() == location.getBlockY() &&
                s.location.getBlockZ() == location.getBlockZ())
                return s;
        return null;
    }

}