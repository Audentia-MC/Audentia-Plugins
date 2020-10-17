package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.damage.PlayerDamage;
import fr.audentia.core.domain.model.location.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ListenerPlayerDamage implements Listener {

    private final PlayerDamage playerDamage;

    public ListenerPlayerDamage(PlayerDamage playerDamage) {
        this.playerDamage = playerDamage;
    }

    public void onPlayerDamage(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;

        boolean receivedDamage = playerDamage.canBeDamaged(player.getUniqueId());

        if (!receivedDamage) {
            event.setCancelled(true);
        }

    }

    public void onPlayerDeathByOtherPlayer(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(entity instanceof Player) || !(damager instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        Player playerDamager = (Player) damager;

        org.bukkit.Location location = playerDamager.getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (player.getHealth() - event.getFinalDamage() <= 0) {
            playerDamage.dealDamage(entity.getUniqueId(), playerDamager.getUniqueId(), domainLocation);
        }

    }

}
