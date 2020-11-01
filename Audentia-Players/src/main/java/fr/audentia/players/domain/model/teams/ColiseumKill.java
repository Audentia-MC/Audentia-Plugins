package fr.audentia.players.domain.model.teams;

import java.util.UUID;

public class ColiseumKill {

    public final UUID killer;
    public final UUID killed;
    public final int timeInSeconds;

    public ColiseumKill(UUID killer, UUID killed, int timeInSeconds) {
        this.killer = killer;
        this.killed = killed;
        this.timeInSeconds = timeInSeconds;
    }

}
