package studio.minekarta.kartaworldreset.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.options.RegenWorldOptions;
import studio.minekarta.kartaworldreset.KartaWorldReset;
import studio.minekarta.kartaworldreset.settings.Config;

public class Reset {

    public static void world() {
        Bukkit.getOnlinePlayers().forEach((player -> {
            Utils.sendTitle(player, "Resetting", "Please wait...", 200);
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
                    KartaWorldReset.getWorldManager().getWorldManager().regenWorld(RegenWorldOptions.world(loadedWorld));
                }
            });
        });
        Bukkit.getOnlinePlayers().forEach((player -> {
            Utils.sendTitle(player, "New Season is begin", "Prepare yourself to new adventure!", 80);
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        } ));
    }
}
