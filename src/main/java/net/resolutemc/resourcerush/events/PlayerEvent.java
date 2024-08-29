package net.resolutemc.resourcerush.events;

import net.resolutemc.resourcerush.ResourceRush;
import net.resolutemc.resourcerush.messages.PlayerMessages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class PlayerEvent implements Listener {

    private final ResourceRush resourceRush;


    public PlayerEvent(ResourceRush resourceRush) {
        this.resourceRush = resourceRush;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent bpe) {
        Block block = bpe.getBlock();
        Location blockLocation = block.getLocation();
        World world = block.getWorld();

        if (!resourceRush.getTimeUtils().isRushing) return;
        if (!resourceRush.getTimeUtils().whiteListedWorlds.contains(world.getName())) return;

        if (resourceRush.getSavedBlockLocation().contains(blockLocation)) return;
        resourceRush.getSavedBlockLocation().add(blockLocation);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();
        Location blockLocation = block.getLocation();
        World world = block.getWorld();
        boolean blockProtection = resourceRush.getConfig().getBoolean("Player-Placed-Block-Protection");
        boolean chatMessage = resourceRush.getConfig().getBoolean("Chat-Message");
        boolean actionBarMessage = resourceRush.getConfig().getBoolean("Action-Bar-Message");
        int amountToMultiplyBy = resourceRush.getConfig().getInt("Multiply-Amount");

        if (!resourceRush.getTimeUtils().isRushing) return;
        if (!resourceRush.getTimeUtils().whiteListedWorlds.contains(world.getName())) return;
        if (resourceRush.getSavedBlockLocation().contains(blockLocation)) {
            if (blockProtection) {
                bbe.setCancelled(true);
            }
            PlayerMessages.sendMessage(player, "Player-Placed-Block-Warning");
            return;
        }

        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;
        if (!chatMessage) return;
        PlayerMessages.sendMessage(player, "Player-Extra-Resource-Chat-Message");
        if (!actionBarMessage) return;
        PlayerMessages.sendActionBarMessage(player, "Player-Extra-Resource-Action-Bar-Message");

        for (ItemStack itemStack : block.getDrops()) {
            Item item = world.dropItem(blockLocation, new ItemStack(itemStack.getType(), itemStack.getAmount() * amountToMultiplyBy));
            BlockDropItemEvent event = new BlockDropItemEvent(block, block.getState(), player, Collections.singletonList(item));
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled() || !event.getItems().contains(item)) {
                item.remove();
            }
        }
    }

}
