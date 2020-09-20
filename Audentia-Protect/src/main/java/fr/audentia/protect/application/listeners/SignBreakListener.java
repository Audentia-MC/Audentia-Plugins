package fr.audentia.protect.application.listeners;

import fr.audentia.protect.domain.HouseAction;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SignBreakListener implements Listener {

    private final HouseAction houseAction;

    public SignBreakListener(HouseAction houseAction) {
        this.houseAction = houseAction;
    }

    @EventHandler
    public void onSignBreak(BlockBreakEvent event) {

        Material type = event.getBlock().getState().getType();

        if (!type.name().toLowerCase().contains("sign")) {
            return;
        }

        org.bukkit.Location location = event.getBlock().getLocation();
        boolean cancel = houseAction.breakSign(new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ()));

        event.setCancelled(cancel);
    }

}
