package studio.minekarta.kartaworldreset.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import studio.minekarta.kartaworldreset.papi.PlaceholderView;
import studio.minekarta.kartaworldreset.settings.Config;
import studio.minekarta.kartaworldreset.utils.Reset;
import studio.minekarta.kartaworldreset.utils.ScheduleTimer;
import studio.minekarta.kartaworldreset.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String adminPermission = "kartaworldreset.admin";
        if(args.length>0){
            if(args[0].equalsIgnoreCase("info")) {
                if(args.length == 1){
                    sender.sendMessage(ScheduleTimer.getCDNormal());
                    return true;
                } else {
                    if(args[1].equalsIgnoreCase("setting")){
                        Utils.runAsPermission(sender, adminPermission, ()-> sender.sendMessage(Config.getSettings().getInfoPrint()), () -> sender.sendMessage(Config.getMessages().noPermission));
                        return true;
                    } else if(args[1].equalsIgnoreCase("worldlist")){
                        Utils.runAsPermission(sender, adminPermission, () -> {
                           StringBuilder worlds = new StringBuilder();
                           Config.getWorldList().getWorlds().forEach(s -> {
                               worlds.append(", ").append(s);
                           });
                           worlds.delete(0, 2);
                           sender.sendMessage(Config.getMessages().worldList.replace("%worlds%", worlds));
                           sender.sendMessage(Config.getMessages().lobby.replace("%lobby%", Config.getWorldList().getLobby()));
                        }, () -> sender.sendMessage(Config.getMessages().noPermission));
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
                    sender.sendMessage(Config.getMessages().reload);
                }, () -> sender.sendMessage(Config.getMessages().noPermission));
                return true;
            }

            if (args[0].equalsIgnoreCase("addworld")) {
                Utils.runAsPermission(sender, adminPermission, () -> {
                    if (args.length < 2) {
                        sender.sendMessage(Config.getMessages().addWorldUsage);
                        return;
                    }
                    String worldName = args[1];
                    List<String> worlds = Config.getWorldList().getWorlds();
                    if (worlds.contains(worldName)) {
                        sender.sendMessage(Config.getMessages().worldAlreadyExists.replace("%world%", worldName));
                    } else {
                        worlds.add(worldName);
                        Config.get().set("Worlds", worlds);
                        Config.saveConfig();
                        Config.reload();
                        sender.sendMessage(Config.getMessages().worldAdded.replace("%world%", worldName));
                    }
                }, () -> sender.sendMessage(Config.getMessages().noPermission));
                return true;
            }

            if (args[0].equalsIgnoreCase("removeworld")) {
                Utils.runAsPermission(sender, adminPermission, () -> {
                    if (args.length < 2) {
                        sender.sendMessage(Config.getMessages().removeWorldUsage);
                        return;
                    }
                    String worldName = args[1];
                    List<String> worlds = Config.getWorldList().getWorlds();
                    if (worlds.contains(worldName)) {
                        worlds.remove(worldName);
                        Config.get().set("Worlds", worlds);
                        Config.saveConfig();
                        Config.reload();
                        sender.sendMessage(Config.getMessages().worldRemoved.replace("%world%", worldName));
                    } else {
                        sender.sendMessage(Config.getMessages().worldNotFound.replace("%world%", worldName));
                    }
                }, () -> sender.sendMessage(Config.getMessages().noPermission));
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                help(sender);
                return true;
            }

            if(args[0].equalsIgnoreCase("reset")){
                Utils.runAsPermission(sender, adminPermission, ()->{
                    Reset.world();
                    Config.getSettings().set();
                }, () -> sender.sendMessage(Config.getMessages().noPermission));
                return true;
            }
            if(args[0].equalsIgnoreCase("autogen")){
                Utils.runAsPermission(sender, adminPermission, ()->{
                    Config.getSettings().set();
                    sender.sendMessage(Config.getMessages().autogen);
                }, () -> sender.sendMessage(Config.getMessages().noPermission));
                return true;
            }
            if (args.length > 1 && args[0].equalsIgnoreCase("papi") && args[1].equalsIgnoreCase("reload")) {
                Utils.runAsPermission(sender, adminPermission, ()->{
                    new PlaceholderView().register();
                    if(PlaceholderAPI.isRegistered("kartaworldreset")){
                        sender.sendMessage(Config.getMessages().papiReloaded);
                    } else {
                        sender.sendMessage(Config.getMessages().papiFailed);
                    }
                }, () -> sender.sendMessage(Config.getMessages().noPermission));
                return true;
            }
            return false;
        }

        help(sender);
        return false;
    }

    void help(CommandSender sender){
        Config.getMessages().help.forEach(sender::sendMessage);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        String adminPermission = "kartaworldreset.admin";

        if (args.length == 1) {
            List<String> subcommands = new ArrayList<>(Arrays.asList("info", "help"));
            if (sender.hasPermission(adminPermission)) {
                subcommands.add("reload");
                subcommands.add("autogen");
                subcommands.add("addworld");
                subcommands.add("removeworld");
                subcommands.add("reset");
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    subcommands.add("papi");
                }
            }
            completions.addAll(subcommands.stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList()));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                List<String> infoSubcommands = new ArrayList<>(Arrays.asList("clock"));
                if (sender.hasPermission(adminPermission)) {
                    infoSubcommands.add("setting");
                    infoSubcommands.add("worldlist");
                }
                completions.addAll(infoSubcommands.stream()
                        .filter(s -> s.startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList()));
            } else if (args[0].equalsIgnoreCase("papi") && sender.hasPermission(adminPermission)) {
                if ("reload".startsWith(args[1].toLowerCase())) {
                    completions.add("reload");
                }
            } else if (args[0].equalsIgnoreCase("removeworld") && sender.hasPermission(adminPermission)) {
                completions.addAll(Config.getWorldList().getWorlds().stream()
                        .filter(s -> s.startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList()));
            } else if (args[0].equalsIgnoreCase("addworld") && sender.hasPermission(adminPermission)) {
                completions.addAll(Bukkit.getWorlds().stream()
                        .map(org.bukkit.World::getName)
                        .filter(s -> !Config.getWorldList().getWorlds().contains(s))
                        .filter(s -> s.startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList()));
            }
        }
        return completions;
    }
}
