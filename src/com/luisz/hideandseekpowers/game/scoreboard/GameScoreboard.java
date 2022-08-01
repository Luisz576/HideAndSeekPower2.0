package com.luisz.hideandseekpowers.game.scoreboard;

import com.luisz.hideandseekpowers.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class GameScoreboard {

    private final Game game;

    public GameScoreboard(Game game){
        this.game = game;
    }

    public void updateRender(){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("hasp", "game");
        obj.setDisplayName(ChatColor.YELLOW + "HASP2.0");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score line1 = obj.getScore("");
        line1.setScore(0);
        Score line2 = obj.getScore(ChatColor.YELLOW + "Time: " + game.getCurrentTime());
        line2.setScore(1);
        Score line3 = obj.getScore("");
        line3.setScore(2);
        Score line4 = obj.getScore(ChatColor.GREEN + "Desenvolvedor:");
        line4.setScore(3);
        Score line5 = obj.getScore(ChatColor.RED + "Luisz576");
        line5.setScore(4);
        for(Player p : game.getEscondedores())
            p.setScoreboard(board);
        for(Player p : game.getProcuradores())
            p.setScoreboard(board);
        for(Player p : game.getEspectadores())
            p.setScoreboard(board);
    }

}