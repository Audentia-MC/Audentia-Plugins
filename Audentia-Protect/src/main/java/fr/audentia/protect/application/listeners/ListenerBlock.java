package fr.audentia.protect.application.listeners;

import fr.audentia.protect.domain.model.Location;
import fr.audentia.protect.domain.portals.NetherPortalProtection;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerBlock implements Listener {

    private final NetherPortalProtection netherPortalProtection;

    public ListenerBlock(NetherPortalProtection netherPortalProtection) {
        this.netherPortalProtection = netherPortalProtection;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortalBreak(BlockBreakEvent event) {

        Block block = event.getBlock();
        World world = block.getWorld();

        if (block.getType() != Material.OBSIDIAN || !world.getName().contains("nether")) {
            return;
        }

        org.bukkit.Location location = block.getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        boolean canBreakBlock = netherPortalProtection.canBreakBlock(event.getPlayer().getUniqueId(), domainLocation);

        if (!canBreakBlock) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortalBreak(BlockPlaceEvent event) {

        Block block = event.getBlock();
        World world = block.getWorld();

        if (!world.getName().contains("nether")) {
            return;
        }

        org.bukkit.Location location = block.getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        boolean canBreakBlock = netherPortalProtection.canPlaceBlock(event.getPlayer().getUniqueId(), domainLocation);

        if (!canBreakBlock) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        World world = block.getWorld();

        if (!world.getName().contains("nether")) {
            return;
        }

        org.bukkit.Location location = block.getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        boolean canInteract = netherPortalProtection.canPlaceBlock(event.getPlayer().getUniqueId(), domainLocation);

        if (!canInteract) {
            event.setCancelled(true);
        }

    }

}
