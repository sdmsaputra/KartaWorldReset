package studio.minekarta.kartaworldreset.settings;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class Messages {

    public final String reload;
    public final String autogen;
    public final String papiReloaded;
    public final String papiFailed;
    public final String noPermission;
    public final String worldList;
    public final String lobby;
    public final List<String> help;

    public Messages(FileConfiguration config) {
        this.reload = translate(config.getString("Messages.reload", "&a[KartaWorldReset] Configuration reloaded!"));
        this.autogen = translate(config.getString("Messages.autogen", "&a[KartaWorldReset] Autogen complete!"));
        this.papiReloaded = translate(config.getString("Messages.papi-reloaded", "&a[KartaWorldReset] PAPI Registered!"));
        this.papiFailed = translate(config.getString("Messages.papi-failed", "&c[KartaWorldReset] PAPI Register is failed!"));
        this.noPermission = translate(config.getString("Messages.no-permission", "&cYou don't have permission to do that."));
        this.worldList = translate(config.getString("Messages.world-list", "&eWorldlist : &f[%worlds%]"));
        this.lobby = translate(config.getString("Messages.lobby", "&eLobby : &f%lobby%"));
        this.help = config.getStringList("Messages.help").stream().map(this::translate).collect(Collectors.toList());
    }

    private String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
