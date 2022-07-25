package com.luisz.hideandseekpowers.controller;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PowersController extends Controller<Class<? extends Power>>{

    public Power createEmptyPowerThatDoesntWork(Class<? extends Power> c){
        try{
            if(has(c))
                return (Power) c.getConstructors()[0].newInstance(null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Power createPower(Class<? extends Power> c, Game game, Player who, Location whereUse){
        try{
            if(has(c))
                return (Power) c.getConstructors()[0].newInstance(game, who, whereUse);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}