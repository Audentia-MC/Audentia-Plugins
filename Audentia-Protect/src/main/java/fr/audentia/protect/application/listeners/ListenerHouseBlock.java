package fr.audentia.protect.application.listeners;

import fr.audentia.protect.domain.house.HouseAction;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class ListenerHouseBlock implements Listener {

    private final HouseAction houseAction;

    public ListenerHouseBlock(HouseAction houseAction) {
        this.houseAction = houseAction;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {

        Block block = event.getBlock();
        boolean cancel = needToCancel(event.getPlayer().getUniqueId(), block);
        event.setCancelled(cancel);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {

        boolean cancel = needToCancel(event.getPlayer().getUniqueId(), event.getBlock());
        event.setCancelled(cancel);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (block == null) {
            return;
        }

        UUID playerUUID = event.getPlayer().getUniqueId();
        boolean cancel = needToCancel(playerUUID, block);
        boolean cancel2 = needToCancel(playerUUID, block.getRelative(event.getBlockFace()));
        event.setCancelled(cancel && cancel2);
    }

    private boolean needToCancel(UUID uuid, Block block) {

        org.bukkit.Location spigotLocation = block.getLocation();
        Location location = new Location(spigotLocation.getBlockX(), spigotLocation.getBlockY(), spigotLocation.getBlockZ());

        boolean canInteract = houseAction.canInteract(uuid, location);
        return !canInteract;
    }

}
