package fr.audentia.core.domain.model.border;

import java.util.Objects;

public class BorderSize {

    public final int size;

    public BorderSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorderSize that = (BorderSize) o;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

}
