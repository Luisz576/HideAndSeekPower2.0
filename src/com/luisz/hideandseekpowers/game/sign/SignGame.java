package com.luisz.hideandseekpowers.game.sign;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.IGame;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SignGame {

    public final String signName, arenaName;
    public final Location location;

    public SignGame(String signName, Location location, String arenaName){
        this.signName = signName;
        this.location = location;
        this.arenaName = arenaName;
    }

    public int join(Player player){
        IGame game = Main.gameController.getByArena(arenaName);
        if(game != null) {
            if(Main.gameController.get(player) != null)
                return 2;
            game.join(player);
            return 0; // join
        }else
            return 1; // arena closed
    }

    public static boolean isSign(Material m){
        return m.toString().toLowerCase().contains("sign");
    }

}