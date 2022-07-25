package com.luisz.hideandseekpowers.game.power;

import org.bukkit.entity.Player;

public interface IPower {
    void onStart();
    void onStop();
    void onOnlyUse();
    boolean thisPowerCanGiveAnother();
    void onNeedWaitDelay(int delay);
    Player getWhoUse();
    int getTimeRun(); // if 0 don't use run
    int getDelayToUse(); // if 0 don't use delay
    void run();
}