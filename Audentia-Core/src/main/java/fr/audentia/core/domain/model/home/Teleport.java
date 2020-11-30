package fr.audentia.core.domain.model.home;

import fr.audentia.core.domain.model.location.Location;

public class Teleport {

    public final long time;
    public final Location location;

    public Teleport(long time, Location location) {
        this.time = time;
        this.location = location;
    }

}
