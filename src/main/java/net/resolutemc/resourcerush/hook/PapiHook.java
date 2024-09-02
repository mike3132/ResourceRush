package net.resolutemc.resourcerush.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.resolutemc.resourcerush.ResourceRush;
import net.resolutemc.resourcerush.configs.ConfigManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiHook extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "ResourceRush";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Mike3132";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String string) {
        if (player == null) return "";
        if (string.equalsIgnoreCase("RushTimer")) {
            if (!ResourceRush.getInstance().getTimeUtils().isTimerRunning) {
                return ConfigManager.MESSAGES.get().getString("Messages." + "Rush-Timer-Not-Active");
            }
            if (ResourceRush.getInstance().getTimeUtils().isRushing) {
                return ConfigManager.MESSAGES.get().getString("Messages." + "Rush-Active-Placeholder") + ResourceRush.getInstance().getTimeUtils().rushDuration();
            }
            return ConfigManager.MESSAGES.get().getString("Messages." + "Rush-No-Active-Placeholder") + ResourceRush.getInstance().getTimeUtils().cooldownTime();
        }
        return null;
    }
}
