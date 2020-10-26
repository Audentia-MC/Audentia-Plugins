package fr.audentia.core.domain.game;

import java.util.UUID;

public interface PlayerMessageSender {

    void sendMessage(UUID playerUUID, String message);

}
