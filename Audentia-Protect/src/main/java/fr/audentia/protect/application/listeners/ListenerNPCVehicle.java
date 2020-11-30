package fr.audentia.protect.application.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class ListenerNPCVehicle implements Listener {

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {

        Entity entered = event.getEntered();
        if (entered instanceof Villager) {
            event.setCancelled(true);
        }

    }

}
