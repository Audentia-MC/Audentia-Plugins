package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.house.BuyHouseAction;
import fr.audentia.protect.domain.house.HouseAction;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerSign implements Listener {

    private final HouseAction houseAction;
    private final BuyHouseAction buyHouseAction;

    public ListenerSign(HouseAction houseAction, BuyHouseAction buyHouseAction) {
        this.houseAction = houseAction;
        this.buyHouseAction = buyHouseAction;
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

        if (!block.getWorld().getName().equals("world")) {
            return;
        }

        Material type = block.getState().getType();

        if (!type.name().toLowerCase().contains("sign")) {
            return;
        }

        Player player = event.getPlayer();
        org.bukkit.Location location = block.getLocation();
        Location modelLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (!this.buyHouseAction.isSecondClick(player.getUniqueId(), modelLocation)) {
            player.sendMessage(ChatUtils.format("<success>Cliquez une deuxième fois pour confirmer l'achat."));
            return;
        }

        String result = houseAction.buyHouse(modelLocation, player.getUniqueId());

        if (result.isEmpty()) {
            return;
        }

        player.sendMessage(ChatUtils.format(result));
    }

}
