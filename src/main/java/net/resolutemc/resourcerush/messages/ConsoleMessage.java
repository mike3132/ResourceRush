package net.resolutemc.resourcerush.messages;

import net.resolutemc.resourcerush.ResourceRush;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConsoleMessage {

    public static void sendConsoleMessageWithoutConfig(CommandSender sender, String key) {
        String message = ColorTranslate.chatColor("&6Resource &eRush &7> " + key);
        sender.sendMessage(message);
    }

    public static void sendMessage(CommandSender sender, String key) {
        File messagesConfig = new File(ResourceRush.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }



}
