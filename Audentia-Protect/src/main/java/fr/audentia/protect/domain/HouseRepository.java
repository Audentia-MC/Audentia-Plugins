package fr.audentia.protect.domain;

import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;

public interface HouseRepository {

    boolean isRegisteredSign(Location location);

    House getHouse(int houseId);

    boolean isBoughtBySign(Location location);

    House getHouse(Location location);

}
