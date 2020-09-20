package fr.audentia.protect.main;

import fr.audentia.protect.application.listeners.SignBreakListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.List;

public class ListenersManager {

    private final Plugin plugin;
    private final AudentiaProtectManagersProvider provider;
    private final List<Listener> listeners;

    public ListenersManager(Plugin plugin, AudentiaProtectManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;

        this.listeners = loadListeners();
    }

    private List<Listener> loadListeners() {

        return Arrays.asList(
                new SignBreakListener(provider.houseAction)
        );

    }

    public void registerListeners() {

        PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        this.listeners.forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

    }

}
