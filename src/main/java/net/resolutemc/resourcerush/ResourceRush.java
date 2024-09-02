package net.resolutemc.resourcerush;

import net.resolutemc.resourcerush.commands.ResourceCommand;
import net.resolutemc.resourcerush.configs.ConfigManager;
import net.resolutemc.resourcerush.events.PlayerEvent;
import net.resolutemc.resourcerush.hook.PapiHook;
import net.resolutemc.resourcerush.messages.ConsoleMessage;
import net.resolutemc.resourcerush.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public final class ResourceRush extends JavaPlugin {

    private static ResourceRush INSTANCE;
    private TimeUtils timeUtils;
    private final List<Location> savedBlockLocation = new ArrayList<>();

    public List<Location> getSavedBlockLocation() {
        return savedBlockLocation;
    }


    @Override
    public void onEnable() {
        INSTANCE = this;
        // API checks
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            ConsoleMessage.sendConsoleMessageWithoutConfig(getServer().getConsoleSender(), "&4ERROR: &cPlaceholder API not found some messages may not work");
            return;
        }
        new PapiHook().register();

        // Plugin startup logic
        ConsoleMessage.sendConsoleMessageWithoutConfig(getServer().getConsoleSender(), "&2Enabled");

        // Command registers
        registerCommands();

        // Event registers
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(this), this);

        // Config loader
        ConfigManager.MESSAGES.create();
        saveDefaultConfig();

        // Starts a new rush timer on load
        this.timeUtils = new TimeUtils(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConsoleMessage.sendConsoleMessageWithoutConfig(getServer().getConsoleSender(), "&4Disabled");
    }

    public static ResourceRush getInstance() {
        return INSTANCE;
    }

    private void registerCommands() {
        new ResourceCommand();
    }

    public TimeUtils getTimeUtils() {
        return this.timeUtils;
    }
}
