package fr.audentia.players.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {

    static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    private ChatUtils() {
    }

    public static void success(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }

    public static void failure(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
    }

    public static String format(String message) {
        String newMessage = message
                .replace("<success> ", ChatColor.GREEN.toString())
                .replace("<error> ", ChatColor.RED.toString())
                .replace("<blue>", ChatColor.BLUE.toString())
                .replace("<black>", ChatColor.BLACK.toString())
                .replace("<red>", ChatColor.RED.toString())
                .replace("<green>", ChatColor.GREEN.toString())
                .replace("<gray>", ChatColor.GRAY.toString())
                .replace("<yellow>", ChatColor.YELLOW.toString())
                .replace("<pink>", ChatColor.LIGHT_PURPLE.toString())
                .replace("<cyan>", ChatColor.AQUA.toString())
                .replace("<white>", ChatColor.WHITE.toString())
                .replace("<blueC>", ChatColor.BLUE + "bleue")
                .replace("<blackC>", ChatColor.BLACK + "noire")
                .replace("<redC>", ChatColor.RED + "rouge")
                .replace("<greenC>", ChatColor.GREEN + "verte")
                .replace("<grayC>", ChatColor.GRAY + "grise")
                .replace("<yellowC>", ChatColor.YELLOW + "jaune")
                .replace("<pinkC>", ChatColor.LIGHT_PURPLE + "rose")
                .replace("<cyanC>", ChatColor.AQUA + "cyan")
                .replace("<whiteC>", ChatColor.WHITE + "blanche");

        return translateColorCodes(newMessage);
    }

    private static String translateColorCodes(String text) {

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++) {

            if (texts[i].equalsIgnoreCase("&")) {

                i++;

                if (texts[i].charAt(0) == '#') {

                    finalText.append(net.md_5.bungee.api.ChatColor.of(texts[i].substring(0, 7)))
                            .append(texts[i].substring(7));

                    continue;
                }

                finalText.append(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                continue;
            }

            finalText.append(texts[i]);

        }

        return finalText.toString();
    }

}
