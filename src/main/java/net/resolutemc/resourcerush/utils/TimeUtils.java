package net.resolutemc.resourcerush.utils;

import net.resolutemc.resourcerush.ResourceRush;
import net.resolutemc.resourcerush.messages.PlayerMessages;
import net.resolutemc.resourcerush.messages.RushBossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    private final ResourceRush resourceRush;
    public List<String> whiteListedWorlds = new ArrayList<>();
    private BukkitTask checkTask;
    private BukkitTask rushTask;
    private long checkTimer;
    private long rushTimer;
    final RushBossBar rushBossBar = new RushBossBar();

    public boolean isRushing;
    public boolean isTimerRunning;

    public TimeUtils(ResourceRush resourceRush) {
        this.resourceRush = resourceRush;

        this.startCheckTimer();
    }

    public void startCheckTimer() {
        whiteListedWorlds.addAll(resourceRush.getConfig().getStringList("World-Whitelist"));
        this.checkTask = Bukkit.getScheduler().runTaskTimer(resourceRush, this::checkTime, 0, 20);
        // This is how long between rushes
        int coolDown = resourceRush.getConfig().getInt("Rush-Cooldown-Time");
        this.checkTimer = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(coolDown);
        this.isTimerRunning = true;
    }

    public void checkTime() {
        if (System.currentTimeMillis() > this.checkTimer) {
            this.startRush();
            this.checkTask.cancel();
        }
    }

    public void startRush() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            rushBossBar.addBar(player);
            PlayerMessages.sendMessage(player, "Player-Rush-Started-Message");
        }
        this.isRushing = true;
        // This is how long a rush will last
        int duration = resourceRush.getConfig().getInt("Rush-Time");
        this.rushTimer = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(duration);
        this.rushTask = Bukkit.getScheduler().runTaskTimer(resourceRush, this::doRush, 0, 20);
    }

    public void doRush() {
        if (System.currentTimeMillis() > this.rushTimer) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                rushBossBar.removeBar(player);
                PlayerMessages.sendMessage(player, "Player-Rush-Stopped-Message");
            }
            this.isRushing = false;
            this.startCheckTimer();
            this.rushTask.cancel();
            resourceRush.getSavedBlockLocation().clear();
        }
    }

    public void disable() {
        this.checkTask.cancel();
        if (this.rushTask != null) {
            this.rushTask.cancel();
        }
        this.isRushing = false;
        this.isTimerRunning = false;
        for (Player player : Bukkit.getOnlinePlayers()) {
            rushBossBar.removeBar(player);
        }
    }

    public String cooldownTime() {
        long currentTime = this.checkTimer - System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(currentTime);
        return simpleDateFormat.format(date);
    }

    public String rushDuration() {
        long currentTime = this.rushTimer - System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(currentTime);
        return simpleDateFormat.format(date);
    }


}
