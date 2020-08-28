package fr.audentia.players.domain.model.roles;

import java.awt.*;

public class Role {

    public final int number;
    public final boolean staff;
    public final boolean player;
    public final int homeCount;
    public final Color color;
    public final String name;

    public Role(RoleBuilder builder) {
        this.number = builder.getNumber();
        this.staff = builder.isStaff();
        this.player = builder.isPlayer();
        this.homeCount = builder.getHomeCount();
        this.color = builder.getColor();
        this.name = builder.getName();
    }

}
