package fr.audentia.players.domain.model;

public class Role {

    public final boolean staff;
    public final boolean player;
    public final int homeCount;

    public Role(boolean staff, boolean player, int homeCount) {
        this.staff = staff;
        this.player = player;
        this.homeCount = homeCount;
    }

}
