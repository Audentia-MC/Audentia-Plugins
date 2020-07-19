package fr.audentia.players.domain.model;

public class Role {

    public final boolean staff;
    public final boolean player;

    public Role(boolean staff, boolean player) {
        this.staff = staff;
        this.player = player;
    }

}
