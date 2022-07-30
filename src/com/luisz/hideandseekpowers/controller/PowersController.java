package com.luisz.hideandseekpowers.controller;

import com.luisz.hideandseekpowers.game.Game;
import com.luisz.hideandseekpowers.game.power.Power;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Random;

public class PowersController extends Controller<Class<? extends Power>>{

    public Power getRandomPower(boolean canGetOneThatCanGiveAnother){
        Power p = null;
        Random r = new Random();
        while(p == null){
            Class<? extends Power> pp = get(r.nextInt(size()));
            p = createEmptyPowerThatDoesntWork(pp);
            if(p.thisPowerCanGiveAnother())
                if(!canGetOneThatCanGiveAnother)
                    p = null;
        }
        return p;
    }
    public Power createEmptyPowerThatDoesntWork(Class<? extends Power> c){
        try{
            if(has(c))
                return (Power) c.getConstructors()[0].newInstance(null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Power createPower(Class<? extends Power> c, @Nonnull Game game, @Nonnull Player who, @Nonnull Location whereUse){
        try{
            if(has(c))
                return (Power) c.getConstructors()[0].newInstance(game, who, whereUse);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAPower(ItemStack item) {
        for(Class<? extends Power> powerC : _data){
            ItemStack powerItem = createEmptyPowerThatDoesntWork(powerC).getDefaultItemAndAmount();
            if(powerItem.getType() == item.getType() &&
                powerItem.getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemMeta().getDisplayName()))
                return true;
        }
        return false;
    }

}