package fr.audentia.core.application.commands;

import fr.audentia.core.domain.event.EventProvider;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandEvent implements CommandExecutor {

    private final EventProvider eventProvider;

    public CommandEvent(EventProvider eventProvider) {
        this.eventProvider = eventProvider;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String nextEvent = eventProvider.getNextEvent();

        sender.sendMessage(ChatUtils.formatWithPrefix(nextEvent));
        return true;
    }

}

