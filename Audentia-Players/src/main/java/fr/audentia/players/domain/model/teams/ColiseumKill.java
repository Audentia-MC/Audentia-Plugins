package fr.audentia.players.domain.model.teams;

import java.time.LocalDateTime;
import java.util.UUID;

public class ColiseumKill {

    public final UUID killer;
    public final UUID killed;
    public final LocalDateTime time;

    public ColiseumKill(UUID killer, UUID killed, LocalDateTime time) {
        this.killer = killer;
        this.killed = killed;
        this.time = time;
    }

}
