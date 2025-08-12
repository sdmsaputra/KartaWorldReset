package studio.minekarta.kartaworldreset.tasks;

import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.core.world.options.RegenWorldOptions;
import studio.minekarta.kartaworldreset.KartaWorldReset;
import studio.minekarta.kartaworldreset.settings.Config;
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
                    Bukkit.getOnlinePlayers().forEach((player -> {
                        Utils.sendTitle(player, "Resetting", "Please wait...", 200);
                        //player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                    }));

                    Config.getWorldList().getWorlds().forEach( world -> {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if(player.getWorld().getName().equalsIgnoreCase(world)){
                                if(Config.getWorldList().getLobby() != null){
                                    player.teleport(Bukkit.getWorld(Config.getWorldList().getLobby()).getSpawnLocation());
                                } else {
                                    if(player.getBedSpawnLocation()!=null){
                                        player.teleport(player.getBedSpawnLocation());
                                    } else {
                                        player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                                    }
                                }
                            }
                        });
                        KartaWorldReset.getWorldManager().getWorldManager().getWorld(world).peek(mvWorld -> {
                            if (mvWorld instanceof LoadedMultiverseWorld) {
                                LoadedMultiverseWorld loadedWorld = (LoadedMultiverseWorld) mvWorld;
                                KartaWorldReset.getWorldManager().getWorldManager().regenWorld(RegenWorldOptions.world(loadedWorld).randomSeed());
                            }
                        });
                    });
                    Bukkit.getOnlinePlayers().forEach((player -> {
                        Utils.sendTitle(player, "New Season is begin", "Prepare yourself to new adventure!", 80);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                    } ));
                }
            }.runTask(KartaWorldReset.getPlugin());
            Config.getSettings().set();
            timer.cancel();
        }
    }
}
