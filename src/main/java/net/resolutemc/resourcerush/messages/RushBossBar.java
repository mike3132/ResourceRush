package net.resolutemc.resourcerush.messages;

import net.resolutemc.resourcerush.ResourceRush;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class RushBossBar {

    private final String barTitle = ColorTranslate.chatColor("" + ResourceRush.getInstance().getConfig().getString("Boss-Bar-Title"));
    private final String barColor = ResourceRush.getInstance().getConfig().getString("Boss-Bar-Color");
    private final String barStyle = ResourceRush.getInstance().getConfig().getString("Boss-Bar-Style");
    private final BossBar bar = Bukkit.createBossBar(barTitle, BarColor.valueOf(barColor), BarStyle.valueOf(barStyle));

    public void addBar(Player player) {
        bar.addPlayer(player);
    }

    public void removeBar(Player player) {
        bar.removePlayer(player);
    }
}
