package fr.audentia.core.main;

import fr.audentia.core.application.listeners.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Arrays;
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

        return Arrays.asList(
                new ListenerPlayerConnect(provider.scoreboardManage, provider.joinGameModeManage),
                new ListenerPlayerInteract(provider.gameStateManage),
                new ListenerNpcInteract(provider.npcInteract),
                new ListenerPlayerDamage(provider.playerDamage, provider.teleportationsManage)
        );

    }

    public void registerListeners() {

        PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        this.listeners.forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

    }

}
