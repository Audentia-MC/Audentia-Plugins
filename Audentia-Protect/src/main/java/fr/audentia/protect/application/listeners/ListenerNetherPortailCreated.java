package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.model.Location;
import fr.audentia.protect.domain.portals.PortalCreateCheck;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListenerNetherPortailCreated implements Listener {

    private final PortalCreateCheck portalCreateCheck;

    public ListenerNetherPortailCreated(PortalCreateCheck portalCreateCheck) {
        this.portalCreateCheck = portalCreateCheck;
    }

    @EventHandler
    public void onNetherPortailCreated(PortalCreateEvent event) {

        boolean notNetherPortal = event.getBlocks().stream()
                .map(BlockState::getType)
                .noneMatch(material -> material == Material.OBSIDIAN);

        if (notNetherPortal) {
            return;
        }

        org.bukkit.Location location = event.getBlocks().get(0).getLocation();
        List<UUID> players = event.getWorld().getNearbyEntities(location, 10, 10, 10).stream()
                .filter(entity -> entity instanceof Player)
                .map(Entity::getUniqueId)
                .collect(Collectors.toList());

        if (players.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        String result = portalCreateCheck.canCreate(players, domainLocation);

        if (result.startsWith("<error>")) {
            event.setCancelled(true);
        }

        players.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> player.sendMessage(ChatUtils.formatWithPrefix(result)));
    }

}
