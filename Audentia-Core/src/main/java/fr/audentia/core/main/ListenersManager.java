package fr.audentia.core.main;

import fr.audentia.core.application.listeners.ListenerNpcInteract;
import fr.audentia.core.application.listeners.ListenerPlayerConnect;
import fr.audentia.core.application.listeners.ListenerPlayerInteract;
import fr.audentia.core.application.listeners.ListenerPlayerMove;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public class ListenersManager {

    private final Plugin plugin;
    private final AudentiaCoreManagersProvider provider;
    private final List<Listener> listeners;

    public ListenersManager(Plugin plugin, AudentiaCoreManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;

        this.listeners = loadListeners();
    }

    private List<Listener> loadListeners() {

        List<Listener> listeners = new ArrayList<>();

        listeners.add(new ListenerPlayerConnect(provider.scoreboardManage));
        listeners.add(new ListenerPlayerMove(provider.gameStateManage));
        listeners.add(new ListenerPlayerInteract(provider.gameStateManage));
        listeners.add(new ListenerNpcInteract(provider.npcInteract));

        return listeners;
    }

    public void registerListeners() {

        PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        this.listeners.forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

    }

}
