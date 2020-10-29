package fr.audentia.protect.domain.house;

import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;

import java.util.List;

public interface HouseRepository {

    boolean isRegisteredSign(Location location);

    House getHouse(int houseId);

    boolean isBoughtBySign(Location location);

    House getHouse(Location location);

    List<House> getAllHouses();

    int getHouseId(Location location);

}
