package me.dlands.endworldreset.settings;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class WorldList {

    private FileConfiguration config;
    private List<String> worlds;
    private String lobby;

    WorldList(FileConfiguration config){
        this.config = config;
        worlds = config.getStringList("Worlds");
        lobby = config.getString("Lobby");
    }

    public List<String> getWorlds() {
        return worlds;
    }

    public String getLobby() {
        return lobby;
    }
}
