package com.luisz.hideandseekpowers.game.power;

import com.luisz.hideandseekpowers.Main;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GamePowerController {

    private int runId = -1;
    private final HashMap<IPower, Integer> powersRunning = new HashMap<>();
    private final HashMap<Player, HashMap<Class<? extends IPower>, Integer>> powersDelay = new HashMap<>();

    public void startGame(){
        if(this.runId == -1)
            this.runId = Main.sc.scheduleSyncRepeatingTask(Main.instance, this::run, 0, 1L);
    }

    private void run(){ //every tick
        for(Player pDelay : this.powersDelay.keySet()){
            for(Class<? extends IPower> cDelay : this.powersDelay.get(pDelay).keySet()){
                if(this.powersDelay.get(pDelay).get(cDelay) <= 1)
                    this.powersDelay.get(pDelay).remove(cDelay);
                else
                    this.powersDelay.get(pDelay).put(cDelay, this.powersDelay.get(pDelay).get(cDelay) - 1);
            }
        }
        for(IPower power : this.powersRunning.keySet()) {
            power.run();
            if(this.powersRunning.get(power) <= 1)
                this.powersRunning.put(power, this.powersRunning.get(power) - 1);
            else {
                this.powersRunning.remove(power);
                power.onStop();
            }
        }
    }

    public void stopGame(){
        if(this.runId != -1){
            Main.sc.cancelTask(this.runId);
            this.runId = -1;
        }
    }

    public boolean usePower(IPower power){
        HashMap<Class<? extends IPower>, Integer> delays = this.powersDelay.get(power.getWhoUse());
        if(delays != null){
            if(delays.containsKey(power.getClass())) {
                power.onNeedWaitDelay(delays.get(power.getClass()));
                return false;
            }
        }else
            delays = new HashMap<>();
        if(power.getDelayToUse() > 0){
            delays.put(power.getClass(), power.getDelayToUse()*20);
            this.powersDelay.put(power.getWhoUse(), delays);
        }
        if(power.getTimeRun() > 0) {
            this.powersRunning.put(power, power.getTimeRun());
            power.onStart();
        }else
            power.onOnlyUse();
        return true;
    }

}