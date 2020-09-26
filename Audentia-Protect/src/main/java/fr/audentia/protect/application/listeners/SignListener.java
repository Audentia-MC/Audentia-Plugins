package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.HouseAction;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    private final HouseAction houseAction;

    public SignListener(HouseAction houseAction) {
        this.houseAction = houseAction;
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent event) {

        Block block = event.getBlock();
        Material type = block.getState().getType();

        if (!type.name().toLowerCase().contains("sign")) {
            return;
        }

        org.bukkit.Location location = block.getLocation();
        boolean cancel = houseAction.breakSign(new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ()));

        event.setCancelled(cancel);
    }

    @EventHandler
    public void onSignInteract(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        Material type = block.getState().getType();

        if (!type.name().toLowerCase().contains("sign")) {
            return;
        }

        org.bukkit.Location location = block.getLocation();
        Player player = event.getPlayer();

        String result = houseAction.buyHouse(new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ()), player.getUniqueId());

        if (result.isEmpty()) {
            return;
        }

        player.sendMessage(ChatUtils.format(result));
    }

}
