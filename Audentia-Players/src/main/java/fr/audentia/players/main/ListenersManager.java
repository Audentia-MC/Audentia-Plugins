package fr.audentia.players.main;

import fr.audentia.players.application.listeners.ListenerChat;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class ListenersManager {

    private final Plugin plugin;
    private final AudentiaPlayersManagersProvider provider;
    private final List<Listener> listeners;

    public ListenersManager(Plugin plugin, AudentiaPlayersManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;

        this.listeners = loadListeners();
    }

    private List<Listener> loadListeners() {

        List<Listener> listeners = new ArrayList<>();

        listeners.add(new ListenerChat(provider.messageFormat));

        return listeners;
    }

    public void registerListeners() {

        PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        this.listeners.forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

    }

}
