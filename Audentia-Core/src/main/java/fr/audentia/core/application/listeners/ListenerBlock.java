package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.model.location.Location;
import fr.audentia.core.domain.protect.CityProtect;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlock implements Listener {

    private final CityProtect cityProtect;

    public ListenerBlock(CityProtect cityProtect) {
        this.cityProtect = cityProtect;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {

        cancelIfNeeded(event.getPlayer(), event, event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {

        cancelIfNeeded(event.getPlayer(), event, event);
    }

    private void cancelIfNeeded(Player player, BlockEvent blockEvent, Cancellable cancellable) {

        org.bukkit.Location spigotLocation = blockEvent.getBlock().getLocation();
        Location location = new Location(spigotLocation.getBlockX(), spigotLocation.getBlockY(), spigotLocation.getBlockZ());

        if (!cityProtect.canInteract(player.getUniqueId(), location)) {
            cancellable.setCancelled(true);
        }

    }

}
