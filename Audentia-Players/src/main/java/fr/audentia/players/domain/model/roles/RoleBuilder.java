package fr.audentia.players.domain.model.roles;

import java.awt.Color;

public class RoleBuilder {

    private Color color;
    private String name;
    private int echelon;

    private RoleBuilder() {
    }

    public static RoleBuilder aRole() {
        return new RoleBuilder();
    }

    public RoleBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public RoleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RoleBuilder withEchelon(int number) {
        this.echelon = number;
        return this;
    }

    public Role build() {
        return new Role(this);
    }

    public int getEchelon() {
        return echelon;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
