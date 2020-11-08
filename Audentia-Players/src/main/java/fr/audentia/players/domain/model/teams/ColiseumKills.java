package fr.audentia.players.domain.model.teams;

public class ColiseumKills {

    public final int kills;

    public ColiseumKills(int kills) {
        this.kills = kills;
    }

    public ColiseumKills add() {
        return new ColiseumKills(kills + 1);
    }

}
