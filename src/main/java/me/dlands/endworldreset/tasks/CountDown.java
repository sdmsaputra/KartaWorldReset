package me.dlands.endworldreset.tasks;

import me.dlands.endworldreset.Endworldreset;
import me.dlands.endworldreset.settings.Config;
import me.dlands.endworldreset.utils.Utils;
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
                        Endworldreset.getWorldManager().getMVWorldManager().regenWorld(world, true, true, null, true);
                    });
                    Bukkit.getOnlinePlayers().forEach((player -> {
                        Utils.sendTitle(player, "New Season is begin", "Prepare yourself to new adventure!", 80);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                    } ));
                }
            }.runTask(Endworldreset.getPlugin());
            Config.getSettings().set();
            timer.cancel();
        }
    }
}
