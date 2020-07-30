package fr.audentia.players.domain.model;

public class Role {

    public final int number;
    public final boolean staff;
    public final boolean player;
    public final int homeCount;

    public Role(int number, boolean staff, boolean player, int homeCount) {
        this.number = number;
        this.staff = staff;
        this.player = player;
        this.homeCount = homeCount;
    }

}
