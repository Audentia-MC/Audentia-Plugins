package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.model.Location;
import fr.audentia.protect.domain.portals.PortalCreateCheck;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class ListenerNetherPortailCreated implements Listener {

    private final PortalCreateCheck portalCreateCheck;

    public ListenerNetherPortailCreated(PortalCreateCheck portalCreateCheck) {
        this.portalCreateCheck = portalCreateCheck;
    }

    @EventHandler
    public void onNetherPortailCreated(PortalCreateEvent event) {

        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        boolean netherPortal = event.getBlocks().stream()
                .map(BlockState::getType)
                .noneMatch(material -> material == Material.OBSIDIAN);

        if (netherPortal) {
            return;
        }

        Player player = (Player) entity;
        org.bukkit.Location location = event.getBlocks().get(0).getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        String result = portalCreateCheck.canCreate(player.getUniqueId(), domainLocation);

        if (result.startsWith("<error>")) {
            event.setCancelled(true);
        }

        player.sendMessage(ChatUtils.format(result));
    }

}
