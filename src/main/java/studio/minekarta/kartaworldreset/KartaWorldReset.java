package studio.minekarta.kartaworldreset;

import org.mvplugins.multiverse.core.MultiverseCoreApi;
import studio.minekarta.kartaworldreset.commands.Commands;
import studio.minekarta.kartaworldreset.papi.PlaceholderView;
import studio.minekarta.kartaworldreset.settings.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class KartaWorldReset extends JavaPlugin {

    private static  Plugin plugin;
    private static MultiverseCoreApi worldManager;
    public static Logger log;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        log = this.getLogger();
        if (!setupMultiverse()) {
            log.log(Level.SEVERE, "Multiverse-Core not found. Disabling KartaWorldReset.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Config.setup();
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            this.getLogger().log(Level.INFO, "PlaceholderAPI detected!");
            new PlaceholderView().register();
        }
        getCommand("kartaworldreset").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupMultiverse() {
        if (getServer().getPluginManager().getPlugin("Multiverse-Core") == null) {
            return false;
        }
        worldManager = MultiverseCoreApi.get();
        return worldManager != null;
    }

    public static MultiverseCoreApi getWorldManager() {
        return worldManager;
    }
    public static Plugin getPlugin() {
        return plugin;
    }
}
