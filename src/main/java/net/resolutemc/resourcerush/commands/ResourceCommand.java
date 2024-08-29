package net.resolutemc.resourcerush.commands;

import net.resolutemc.resourcerush.ResourceRush;
import net.resolutemc.resourcerush.messages.ConsoleMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResourceCommand implements CommandExecutor {

    public ResourceCommand() {
        ResourceRush.getInstance().getCommand("ResourceRush").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args[0].equalsIgnoreCase("Reload")) {
            if (!sender.hasPermission("ResourceRush.Command.Reload")) {
                ConsoleMessage.sendMessage(sender, "No-Permission");
                return false;
            }
            ConsoleMessage.sendConsoleMessageWithoutConfig(sender, "&4Stopping all rushes in preparation to reload config files");
            ResourceRush.getInstance().getTimeUtils().disable();
            ResourceRush.getInstance().reloadConfig();
            ConsoleMessage.sendConsoleMessageWithoutConfig(sender, "&2config reloaded in " + (System.currentTimeMillis() - 1));
            ConsoleMessage.sendConsoleMessageWithoutConfig(sender, "&6Starting new rushes with current config files");
            ResourceRush.getInstance().getTimeUtils().startCheckTimer();
            return false;
        }

        if (args[0].equalsIgnoreCase("Start")) {
            if (!sender.hasPermission("ResourceRush.Command.Start")) {
                ConsoleMessage.sendMessage(sender, "No-Permission");
                return false;
            }
            ConsoleMessage.sendMessage(sender, "Admin-Force-Start-Rush");
            ResourceRush.getInstance().getTimeUtils().disable();
            ResourceRush.getInstance().getTimeUtils().startCheckTimer();
            return false;
        }

        if (args[0].equalsIgnoreCase("Stop")) {
            if (!sender.hasPermission("ResourceRush.Command.Stop")) {
                ConsoleMessage.sendMessage(sender, "No-Permission");
                return false;
            }
            ConsoleMessage.sendMessage(sender, "Admin-Force-Stop-Rush");
            ResourceRush.getInstance().getTimeUtils().disable();
            return false;
        }

        return false;
    }
}
