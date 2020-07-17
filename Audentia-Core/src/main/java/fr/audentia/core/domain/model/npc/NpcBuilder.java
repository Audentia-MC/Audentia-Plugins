package fr.audentia.core.domain.model.npc;

public class NpcBuilder {

    public String name;
    public int x;
    public int y;
    public int z;
    public float yaw;
    public float pitch;

    private NpcBuilder() {
    }

    public static NpcBuilder aNpcLocation() {
        return new NpcBuilder();
    }

    public NpcBuilder withX(int x) {
        this.x = x;
        return this;
    }

    public NpcBuilder withY(int y) {
        this.y = y;
        return this;
    }

    public NpcBuilder withZ(int z) {
        this.z = z;
        return this;
    }

    public NpcBuilder withYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public NpcBuilder withPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public NpcBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Npc build() {
        return new Npc(this);
    }

}
