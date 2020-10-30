package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.house.HouseCreation;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerHouseCreationInterract implements Listener {

    private final HouseCreation houseCreation;

    public ListenerHouseCreationInterract(HouseCreation houseCreation) {
        this.houseCreation = houseCreation;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

//        float yaw = Math.abs(event.getPlayer().getLocation().getYaw());
//
//        char blockFace = 'S';
//
//        if (45 <= yaw && yaw <= 135) {
//            blockFace = 'W';
//        }
//
//        if (135 <= yaw && yaw <= 225) {
//            blockFace = 'S';
//        }
//
//        if (225 <= yaw && yaw <= 315) {
//            blockFace = 'E';
//        }
//
//        if (315 <= yaw || yaw <= 45) {
//            blockFace = 'N';
//        }

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
        }

    }

}
