package fr.audentia.core.domain.damage;

import fr.audentia.core.domain.model.location.Location;

public interface ColiseumLocationRepository {

    Location getColiseumLocation();

    int getColiseumSize();

}
