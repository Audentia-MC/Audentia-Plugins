package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.model.Location;
import fr.audentia.protect.domain.portals.PortalCreateCheck;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListenerNetherPortailCreated implements Listener {

    private final Plugin plugin;
    private final PortalCreateCheck portalCreateCheck;
    private final List<UUID> players = new ArrayList<>();

    public ListenerNetherPortailCreated(Plugin plugin, PortalCreateCheck portalCreateCheck) {
        this.plugin = plugin;
        this.portalCreateCheck = portalCreateCheck;
    }

    @EventHandler
    public void onPlayerInteractInObsidian(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getType() != Material.OBSIDIAN) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.FLINT_AND_STEEL) {
            return;
        }

        players.add(player.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                players.clear();
            }
        }.runTaskLaterAsynchronously(plugin, 20 * 2);
    }

    @EventHandler
    public void onNetherPortailCreated(PortalCreateEvent event) {

        boolean notNetherPortal = event.getBlocks().stream()
                .map(BlockState::getType)
                .noneMatch(material -> material == Material.OBSIDIAN);

        if (notNetherPortal) {
            return;
        }

        if (players.isEmpty()) {
            return;
        }

        Player player = Bukkit.getPlayer(players.get(0));

        if (player == null) {
            return;
        }

        org.bukkit.Location location = event.getBlocks().get(0).getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        String result = portalCreateCheck.canCreate(player.getUniqueId(), domainLocation);

        if (result.startsWith("<error>")) {
            event.setCancelled(true);
        }

        player.sendMessage(ChatUtils.format(result));
    }

}
