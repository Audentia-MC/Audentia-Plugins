package fr.audentia.protect.application.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ListenerBlockBreakEvent implements Listener {

    private final Plugin plugin;

    public ListenerBlockBreakEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(EntityExplodeEvent event) {

        List<Block> blocks = new ArrayList<>(event.blockList());
        event.blockList().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                blocks.forEach(block -> block.getWorld().getBlockAt(block.getLocation()).setType(block.getType()));
            }
        }.runTaskLater(plugin, 10);

    }

}
