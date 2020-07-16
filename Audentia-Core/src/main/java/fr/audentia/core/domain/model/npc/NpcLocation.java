package fr.audentia.core.domain.model.npc;

public class NpcLocation {

    public final int x;
    public final int y;
    public final int z;
    public final float yaw;
    public final float pitch;

    public NpcLocation(NpcLocationBuilder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.yaw = builder.yaw;
        this.pitch = builder.pitch;
    }

}
