package studio.minekarta.kartaworldreset.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import studio.minekarta.kartaworldreset.settings.Config;
import studio.minekarta.kartaworldreset.utils.ScheduleTimer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderView extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "kartaworldreset";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MinekartaStudio";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equals("normal")){
            return ScheduleTimer.getCDNormal();
        } else if (params.equals("nextReset")) {
            return Config.get().get("Save.nextReset") + " " + Config.get().get("Config.time");
        } else if (params.equals("short")) {
            return ScheduleTimer.getCDShort();
        }
        return "[KartaWorldReset] Error";
    }
}
