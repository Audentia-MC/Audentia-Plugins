package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.damage.PlayerDamage;
import fr.audentia.core.domain.home.TeleportationsManage;
import fr.audentia.core.domain.model.location.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class ListenerPlayerDamage implements Listener {

    private final PlayerDamage playerDamage;
    private final TeleportationsManage teleportationsManage;

    public ListenerPlayerDamage(PlayerDamage playerDamage, TeleportationsManage teleportationsManage) {
        this.playerDamage = playerDamage;
        this.teleportationsManage = teleportationsManage;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;

        org.bukkit.Location location = player.getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        boolean receivedDamage = playerDamage.canBeDamaged(player.getUniqueId(), domainLocation);

        if (!receivedDamage) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerDeathByOtherPlayer(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(entity instanceof Player) || !(damager instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        Player playerDamager = (Player) damager;

        teleportationsManage.cancelIfRegistered(player.getUniqueId());

        org.bukkit.Location location = player.getLocation();
        Location domainLocation = new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        boolean receivedDamage = playerDamage.canBeDamaged(player.getUniqueId(), damager.getUniqueId(), domainLocation);

        if (!receivedDamage) {
            event.setCancelled(true);
            return;
        }

        if (player.getHealth() - event.getFinalDamage() <= 0) {
            playerDamage.executeDeath(entity.getUniqueId(), playerDamager.getUniqueId(), domainLocation);
        }

    }

}
