package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.house.HouseCreation;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ListenerHouseCreationInteract implements Listener {

    private final HouseCreation houseCreation;

    public ListenerHouseCreationInteract(HouseCreation houseCreation) {
        this.houseCreation = houseCreation;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        org.bukkit.Location spigotLocation = block.getLocation();
        Location location = new Location(spigotLocation.getBlockX(), spigotLocation.getBlockY(), block.getZ());

        char blockFace = event.getBlockFace().name().charAt(0);

        Player player = event.getPlayer();
        String result = houseCreation.onInteract(player.getUniqueId(), location, blockFace);

        if (!result.isEmpty()) {
            player.sendMessage(ChatUtils.format(result));
            event.setCancelled(true);
        }

    }

}
