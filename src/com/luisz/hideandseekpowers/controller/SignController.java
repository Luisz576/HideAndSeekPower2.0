package com.luisz.hideandseekpowers.controller;

import com.luisz.hideandseekpowers.game.sign.SignGame;
import com.luisz.hideandseekpowers.game.sign.SignsConfig;
import org.bukkit.Location;

public class SignController extends Controller<SignGame>{

    public void loadSigns(SignsConfig config){
        this._data.addAll(config.getAll());
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