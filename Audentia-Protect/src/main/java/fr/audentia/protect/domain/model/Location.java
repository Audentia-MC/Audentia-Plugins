package fr.audentia.protect.domain.model;

import java.util.Objects;

public class Location {

    public final int x;
    public final int y;
    public final int z;

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y &&
                z == location.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public double distanceSquared(Location location) {
        return Math.pow(x - location.x, 2) + Math.pow(y - location.y, 2) + Math.pow(z - location.z, 2);
    }

    public double distanceSquared2D(Location location) {
        return Math.pow(x - location.x, 2) + Math.pow(z - location.z, 2);
    }

}
