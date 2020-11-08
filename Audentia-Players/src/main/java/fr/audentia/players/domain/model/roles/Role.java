package fr.audentia.players.domain.model.roles;

import java.awt.Color;

public class Role {

    public final Color color;
    public final String name;
    public final int echelon;

    public Role(RoleBuilder builder) {
        this.color = builder.getColor();
        this.name = builder.getName();
        this.echelon = builder.getEchelon();
    }

    public boolean isStaff() {
        return echelon >= 500;
    }

    public boolean isPlayer() {
        return echelon == 1;
    }

    public int getHomeCount() {
        return isStaff() ? 1_000 : Math.min(3, echelon / 100);
    }

    public boolean hasMaxPermission() {
        return echelon >= 888;
    }

    public boolean hasModerationPermission() {
        return echelon >= 777;
    }

    public boolean hasBuilderPermission() {
        return echelon > 755;
    }

    public boolean isVipPlayer() {
        return echelon >= 400;
    }

    public boolean hasHousePermission(int houseLevel) {
        return houseLevel <= (echelon / 100);
    }

}
