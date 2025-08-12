package me.dlands.endworldreset.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.dlands.endworldreset.papi.PlaceholderView;
import me.dlands.endworldreset.settings.Config;
import me.dlands.endworldreset.utils.ScheduleTimer;
import me.dlands.endworldreset.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String adminPermission = "endworldreset.admin";
        if(args.length>0){
            if(args[0].equalsIgnoreCase("info")) {
                if(args.length == 1){
                    sender.sendMessage(ScheduleTimer.getCDNormal());
                    return true;
                } else {
                    if(args[1].equalsIgnoreCase("setting")){
                        Utils.runAsPermission(sender, adminPermission, ()-> sender.sendMessage(Config.getSettings().getInfoPrint()));
                        return true;
                    } else if(args[1].equalsIgnoreCase("worldlist")){
                        Utils.runAsPermission(sender, adminPermission, () -> {
                           StringBuilder message = new StringBuilder();
                           Config.getWorldList().getWorlds().forEach(s -> {
                               message.append(", " + s);
                           });
                           message.delete(0, 2);
                           sender.sendMessage("Worldlist : [" + message +"]");
                           sender.sendMessage("Lobby : " + Config.getWorldList().getLobby());
                        });
                        return true;
                    } else if(args[1].equalsIgnoreCase("clock")){
                        sender.sendMessage(Utils.getClockInfo());
                        return true;
                    }
                }
            }

            if(args[0].equalsIgnoreCase("reload")){
                Utils.runAsPermission(sender, adminPermission, ()->{
                    Config.reload();
                    sender.sendMessage("[Endworldreset] Configuration reloaded!");
                });
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                help(sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("autogen")){
                Utils.runAsPermission(sender, adminPermission, ()->{
                    sender.sendMessage("[Endworldreset] Autogen complete!");
                    Config.getSettings().set();
                });
                return true;
            }
            if(args[0].equalsIgnoreCase("papi") && args[1].equalsIgnoreCase("reload")){
                Utils.runAsPermission(sender, adminPermission, ()->{
                    new PlaceholderView().register();
                    if(PlaceholderAPI.isRegistered("endworldreset")){
                        sender.sendMessage("[Endworldreset] PAPI Registered!");
                    } else {
                        sender.sendMessage("[Endworldreset] PAPI Register is failed!");
                    }
                });
                return true;
            }
            return false;
        }

        help(sender);
        return false;
    }

    void help(CommandSender sender){
        sender.sendMessage("Endworldreset Plugin\n" +
                "[Usages]:\n" +
                "/endworldreset reload           Reload config plugin (Admin)\n" +
                "/endworldreset autogen          Auto generate config (Admin)\n" +
                "/endworldreset info             Show time left\n" +
                "/endworldreset info setting     Show config info (Admin)\n" +
                "/endworldreset info worldlist   Show world reset list (Admin)\n" +
                "/endworldreset info clock       Show system clock" +
                "/endworldreset papi reload      Reload placeholder expansion");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] complition= {};
        if(args.length == 1){
            complition = new String[]{"reload", "autogen", "info"};
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                complition = new String[]{"reload", "autogen", "info", "papi"};
            }
        }
        if(args.length == 2){
            switch (args[0]){
                case "info":
                    complition = new String[]{"setting", "worldlist", "clock"};
                    break;
                case "papi":
                    complition = new String[]{"reload"};
                    break;
            }
        }
        return Arrays.asList(complition);
    }
}
