package me.dlands.endworldreset.settings;

import me.dlands.endworldreset.Endworldreset;
import me.dlands.endworldreset.utils.ScheduleTimer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

public class Config {
    private static File file;
    private static FileConfiguration config;
    private static Settings settings;
    private static WorldList worldList;

    public static void setup(){
        file = new File(Endworldreset.getPlugin().getDataFolder(), "config.yml");
        if(!file.exists()){
            try {
                InputStream in = Endworldreset.getPlugin().getResource("config.yml");
                file.mkdirs();
                file.createNewFile();
                Files.copy(in, Paths.get(file.getPath()), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reload();
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save(){
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
            config.set("Save.nextReset", date.format(settings.getNextReset().getTime()));
            config.set("Config.time", time.format(settings.getNextReset().getTime()));
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload(){
        config = YamlConfiguration.loadConfiguration(file);
        settings = new Settings(config);
        worldList = new WorldList(config);
        if(settings.getNextReset() == null){
            settings.set();
        }
        settings.setup();
        ScheduleTimer.init();
    }

    public static Settings getSettings() {
        return settings;
    }

    public static WorldList getWorldList() {
        return worldList;
    }
}
