package fr.audentia.core.domain.model.border;

import java.util.Objects;

public class BorderLocation {

    public final int x;
    public final int z;

    public BorderLocation(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorderLocation that = (BorderLocation) o;
        return x == that.x &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

}
