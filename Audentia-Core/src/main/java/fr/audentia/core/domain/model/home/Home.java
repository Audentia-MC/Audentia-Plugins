package fr.audentia.core.domain.model.home;

import java.util.Objects;

public class Home {

    public final String name;
    public final int x;
    public final int y;
    public final int z;

    public Home(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home that = (Home) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
