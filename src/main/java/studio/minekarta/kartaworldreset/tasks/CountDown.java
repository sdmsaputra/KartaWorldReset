package studio.minekarta.kartaworldreset.tasks;

import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.core.world.options.RegenWorldOptions;
import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.options.RegenWorldOptions;
import studio.minekarta.kartaworldreset.KartaWorldReset;
import studio.minekarta.kartaworldreset.settings.Config;
import studio.minekarta.kartaworldreset.utils.Reset;
import studio.minekarta.kartaworldreset.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;

public class CountDown extends TimerTask {

     Timer timer;

    public CountDown(Timer timer){
        this.timer = timer;
    }

    int counter = 10;

    @Override
    public void run() {
        if(counter>0) {
            Bukkit.getOnlinePlayers().forEach((player -> {
                Utils.sendTitle(player, "Reset World The End!", "will begin on " + counter + " second(s).", 200);
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 1);
            }));
            counter--;
        }
        else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Reset.world();
                        }
                    }.runTask(KartaWorldReset.getPlugin());
            Config.getSettings().set();
            timer.cancel();
        }
    }
}
