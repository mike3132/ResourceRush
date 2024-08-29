package net.resolutemc.resourcerush.messages;

import org.bukkit.ChatColor;

public class ColorTranslate {

    public static String chatColor(String chatColor) {
        return ChatColor.translateAlternateColorCodes('&', chatColor);
    }
}
