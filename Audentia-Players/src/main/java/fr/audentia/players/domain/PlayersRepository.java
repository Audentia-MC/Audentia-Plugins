package fr.audentia.players.domain;

import java.util.UUID;

public interface PlayersRepository {

    void addPlayerIfNotRegistered(UUID playerUUID);

}
