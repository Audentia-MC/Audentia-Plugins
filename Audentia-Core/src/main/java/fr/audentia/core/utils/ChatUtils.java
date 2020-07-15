package fr.audentia.core.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {

    private ChatUtils() {
    }

    public static void success(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }

    public static void failure(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
    }

    public static String format(String message) {
        return message
                .replace("<success> ", ChatColor.GREEN.toString())
                .replace("<error> ", ChatColor.RED.toString())
                .replace("<blueC>", ChatColor.BLUE + "bleue")
                .replace("<blackC>", ChatColor.BLACK + "noire")
                .replace("<redC>", ChatColor.RED + "rouge")
                .replace("<greenC>", ChatColor.GREEN + "verte")
                .replace("<grayC>", ChatColor.GRAY + "grise")
                .replace("<yellowC>", ChatColor.YELLOW + "jaune")
                .replace("<pinkC>", ChatColor.LIGHT_PURPLE + "rose")
                .replace("<cyanC>", ChatColor.AQUA + "cyan")
                .replace("<whiteC>", ChatColor.WHITE + "blanche")
                .replace("<blue>", ChatColor.BLUE + "")
                .replace("<black>", ChatColor.BLACK + "")
                .replace("<red>", ChatColor.RED + "")
                .replace("<green>", ChatColor.GREEN + "")
                .replace("<gray>", ChatColor.GRAY + "")
                .replace("<yellow>", ChatColor.YELLOW + "")
                .replace("<pink>", ChatColor.LIGHT_PURPLE + "")
                .replace("<cyan>", ChatColor.AQUA + "")
                .replace("<white>", ChatColor.WHITE + "");
    }

}
