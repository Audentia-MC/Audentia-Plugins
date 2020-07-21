package fr.audentia.core.domain.model.border;

public class BorderLocation {

    public final int x;
    public final int y;

    public BorderLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorderLocation that = (BorderLocation) o;
        return x == that.x &&
                y == that.y;
    }

}
