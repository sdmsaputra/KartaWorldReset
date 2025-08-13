package studio.minekarta.kartaworldreset.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean runAsPermission(CommandSender sender, String permissionName, Runnable runnable, Runnable noPermission){
        boolean isHas = sender.hasPermission(permissionName);
        if (!isHas) {
            noPermission.run();
        } else {
            runnable.run();
        }
        return isHas;
    }

    public static void sendTitle(Player player, String title, String subtitle, int stay){
        player.sendTitle(title, subtitle, 2, stay, 5);
    }

    public static String getClockInfo(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

}
