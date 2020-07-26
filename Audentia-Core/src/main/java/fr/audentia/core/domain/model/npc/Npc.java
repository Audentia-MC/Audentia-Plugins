package fr.audentia.core.domain.model.npc;

import java.util.Objects;

public class Npc {

    public final String name;
    public final int x;
    public final int y;
    public final int z;
    public final float yaw;
    public final float pitch;

    public Npc(NpcBuilder builder) {
        this.name = builder.name;
        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.yaw = builder.yaw;
        this.pitch = builder.pitch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Npc npc = (Npc) o;
        return x == npc.x &&
                y == npc.y &&
                z == npc.z &&
                Float.compare(npc.yaw, yaw) == 0 &&
                Float.compare(npc.pitch, pitch) == 0 &&
                Objects.equals(name, npc.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y, z, yaw, pitch);
    }

}
