package fr.audentia.players.domain.model.teams;

import java.util.List;

public class ColiseumKills {

    public final List<ColiseumKill> kills;

    public ColiseumKills(List<ColiseumKill> kills) {
        this.kills = kills;
    }

    public void add(ColiseumKill kill) {
        this.kills.add(kill);
    }

}
