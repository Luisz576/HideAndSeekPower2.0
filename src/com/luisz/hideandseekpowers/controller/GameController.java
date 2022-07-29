package com.luisz.hideandseekpowers.controller;

import com.luisz.hideandseekpowers.game.IGame;
import org.bukkit.entity.Player;

public class GameController extends Controller<IGame>{
    @Override
    public boolean add(IGame a) {
        for(IGame g : this._data)
            if(g.getArenaName().equalsIgnoreCase(a.getArenaName().toLowerCase()))
                return false;
        this._data.add(a);
        return true;
    }

    @Override
    public boolean remove(IGame a) {
        IGame g = null;
        for(IGame game : this._data){
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
        IGame game = this._data.remove(index);
        if(game != null){
            game.closeGame();
            return true;
        }
        return false;
    }

    @Override
    public IGame get(int index) {
        return super.get(index);
    }

    public IGame getByArena(String arenaName) {
        for(IGame g : this._data)
            if(g.getArenaName().equalsIgnoreCase(arenaName))
                return g;
        return null;
    }

    public IGame get(Player player){
        for(IGame g : this._data)
            if(g.isPlayerInsideThisGame(player))
                return g;
        return null;
    }
}