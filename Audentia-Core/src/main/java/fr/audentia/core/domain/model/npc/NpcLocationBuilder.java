package fr.audentia.core.domain.model.npc;

public class NpcLocationBuilder {

    public int x;
    public int y;
    public int z;
    public float yaw;
    public float pitch;

    private NpcLocationBuilder() {
    }

    public static NpcLocationBuilder aNpcLocation() {
        return new NpcLocationBuilder();
    }

    public NpcLocationBuilder withX(int x) {
        this.x = x;
        return this;
    }

    public NpcLocationBuilder withY(int y) {
        this.y = y;
        return this;
    }

    public NpcLocationBuilder withZ(int z) {
        this.z = z;
        return this;
    }

    public NpcLocationBuilder withYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public NpcLocationBuilder withPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public NpcLocation build() {
        return new NpcLocation(this);
    }

}
