package com.luisz.hideandseekpowers.controller;

import com.luisz.hideandseekpowers.game.Game;
import org.bukkit.entity.Player;

public class GameController extends Controller<Game>{
    @Override
    public boolean add(Game a) {
        for(Game g : this._data)
            if(g.getArenaName().equalsIgnoreCase(a.getArenaName().toLowerCase()))
                return false;
        this._data.add(a);
        return true;
    }

    @Override
    public boolean remove(Game a) {
        Game g = null;
        for(Game game : this._data){
            if(game.getArenaName().equalsIgnoreCase(a.getArenaName())) {
                g = game;
                break;
            }
        }
        if(g != null && this._data.remove(g)){
            g.closeGame();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(int index) {
        Game game = this._data.remove(index);
        if(game != null){
            game.closeGame();
            return true;
        }
        return false;
    }

    @Override
    public Game get(int index) {
        return super.get(index);
    }

    public Game getByArena(String arenaName) {
        for(Game g : this._data)
            if(g.getArenaName().equalsIgnoreCase(arenaName))
                return g;
        return null;
    }

    public Game get(Player player){
        for(Game g : this._data)
            if(g.isPlayerInsideThisGame(player))
                return g;
        return null;
    }

    public boolean isInSame(Player p1, Player p2) {
        Game a = get(p1);
        Game b = get(p2);
        if(a != null & b != null)
            return a.getArenaName().equalsIgnoreCase(b.getArenaName());
        return false;
    }

}