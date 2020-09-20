package fr.audentia.protect.domain;

import fr.audentia.protect.domain.model.Location;

public interface HouseRepository {

    boolean isRegisteredSign(Location location);

}
