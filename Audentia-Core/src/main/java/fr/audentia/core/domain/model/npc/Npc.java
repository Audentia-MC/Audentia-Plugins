package fr.audentia.core.domain.model.npc;

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

}
