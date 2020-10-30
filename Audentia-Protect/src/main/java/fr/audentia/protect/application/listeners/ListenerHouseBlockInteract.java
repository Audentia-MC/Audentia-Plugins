package fr.audentia.protect.application.listeners;

import fr.audentia.protect.domain.house.HouseAction;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class ListenerHouseBlockInteract implements Listener {

    private final HouseAction houseAction;

    public ListenerHouseBlockInteract(HouseAction houseAction) {
        this.houseAction = houseAction;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        event.setCancelled(isCancel(uuid, event.getBlockPlaced()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        event.setCancelled(isCancel(uuid, event.getBlock()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {


        UUID uuid = event.getPlayer().getUniqueId();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        event.setCancelled(isCancel(uuid, block));
    }

    private boolean isCancel(UUID uuid, Block block) {

        org.bukkit.Location spigotLocation = block.getLocation();

        Location location = new Location(spigotLocation.getBlockX(), spigotLocation.getBlockY(), spigotLocation.getBlockZ());
        return !houseAction.canInteract(uuid, location);
    }

}
