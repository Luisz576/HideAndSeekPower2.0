package com.luisz.hideandseekpowers.game.sign;

import com.luisz.hideandseekpowers.Main;
import com.luisz.hideandseekpowers.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
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
        Game game = Main.gameController.getByArena(arenaName);
        if(game != null) {
            if(Main.gameController.get(player) != null)
                return 2;
            game.join(player);
            return 0; // join
        }else
            return 1; // arena closed
    }

    public void _update(){
        Sign sign = (Sign) this.location.getBlock().getState();
        Game game = Main.gameController.getByArena(this.arenaName);
        sign.setLine(0, ChatColor.BLACK + "[HASP2.0]");
        sign.setLine(1, ChatColor.BLACK + this.arenaName);
        sign.setLine(2, ChatColor.BLACK + "0/0");
        sign.setLine(3, ChatColor.RED + "[FECHADO]");
        if(game != null){
            switch (game.getGameState()){
                case RECRUITING:
                    sign.setLine(2, ChatColor.YELLOW + "" + game.getAmountOfPlayers() + "/" + game.getMaxPlayers());
                    sign.setLine(3, ChatColor.GREEN + "[RECRUTANDO]");
                break;
                case HIDING:
                case GAMEPLAY:
                    sign.setLine(2, ChatColor.YELLOW + "" + game.getAmountOfPlayers() + "/" + game.getMaxPlayers());
                    sign.setLine(3, ChatColor.GREEN + "[EM JOGO]");
                break;
                case STOPING:
                    sign.setLine(2, ChatColor.YELLOW + "0/0");
                    sign.setLine(3, ChatColor.YELLOW + "[RESETANDO]");
                break;
            }
        }
        sign.update();
    }

    public static boolean isSign(Material m){
        return m.toString().toLowerCase().contains("sign");
    }

}