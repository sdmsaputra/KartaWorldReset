package me.dlands.endworldreset.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dlands.endworldreset.settings.Config;
import me.dlands.endworldreset.utils.ScheduleTimer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderView extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "endworldreset";
    }

    @Override
    public @NotNull String getAuthor() {
        return "dlands";
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
        return "[Endresetworld] Error";
    }
}
