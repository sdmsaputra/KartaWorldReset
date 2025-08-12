package studio.minekarta.kartaworldreset;

import com.onarandombox.MultiverseCore.MultiverseCore;
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
        getCommand("kartaworldreset").setExecutor(new Commands());
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
