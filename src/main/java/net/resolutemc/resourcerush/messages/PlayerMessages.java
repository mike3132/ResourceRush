package net.resolutemc.resourcerush.messages;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.resolutemc.resourcerush.ResourceRush;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerMessages {

    public static String sendPlayerMessageWithoutConfig(String key) {
        return ColorTranslate.chatColor("&6Resource &eRush &7> " + key);
    }

    public static void sendMessage(Player player, String key) {
        File messagesConfig = new File(ResourceRush.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendPlaceholderMessage(Player player, String key, String time) {
        File messagesConfig = new File(ResourceRush.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        message = message.replace("%ResourceRush_Cooldown%", time);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendActionBarMessage(Player player, String key) {
        File messagesConfig = new File(ResourceRush.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(ChatColor.translateAlternateColorCodes('&', message)));
    }

}
