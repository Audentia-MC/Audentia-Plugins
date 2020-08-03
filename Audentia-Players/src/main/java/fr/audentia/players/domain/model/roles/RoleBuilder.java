package fr.audentia.players.domain.model.roles;

import java.awt.*;

public class RoleBuilder {

    private int number;
    private boolean staff;
    private boolean player;
    private int homeCount;
    private Color color;
    private String name;

    private RoleBuilder() {
    }

    public static RoleBuilder aRole() {
        return new RoleBuilder();
    }

    public RoleBuilder withNumber(int number) {
        this.number = number;
        return this;
    }

    public RoleBuilder isStaff(boolean staff) {
        this.staff = staff;
        return this;
    }

    public RoleBuilder isPlayer(boolean player) {
        this.player = player;
        return this;
    }

    public RoleBuilder withHomeCount(int homeCount) {
        this.homeCount = homeCount;
        return this;
    }

    public RoleBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public RoleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Role build() {
        return new Role(this);
    }

    public int getNumber() {
        return number;
    }

    public boolean isStaff() {
        return staff;
    }

    public boolean isPlayer() {
        return player;
    }

    public int getHomeCount() {
        return homeCount;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
