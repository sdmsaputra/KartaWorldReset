package me.dlands.endworldreset;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.dlands.endworldreset.commands.Commands;
import me.dlands.endworldreset.papi.PlaceholderView;
import me.dlands.endworldreset.settings.Config;
import me.dlands.endworldreset.utils.ScheduleTimer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Endworldreset extends JavaPlugin {

    private static  Plugin plugin;
    private static MultiverseCore worldManager;
    public static Logger log;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        worldManager = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        log = this.getLogger();
        Config.setup();
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            this.getLogger().log(Level.INFO, "PlaceholderAPI detected!");
            new PlaceholderView().register();
        }
        getCommand("endworldreset").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MultiverseCore getWorldManager() {
        return worldManager;
    }
    public static Plugin getPlugin() {
        return plugin;
    }
}
