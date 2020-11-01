package fr.audentia.core.domain.protect;

import fr.audentia.core.domain.model.location.Location;

public interface CityInfosRepository {

    Location getCityLocation();

    int getCityRadius();

}
