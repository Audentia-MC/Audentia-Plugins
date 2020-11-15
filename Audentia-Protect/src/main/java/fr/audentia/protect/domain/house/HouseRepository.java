package fr.audentia.protect.domain.house;

import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;

import java.util.List;

public interface HouseRepository {

    boolean isRegisteredSign(Location location);

    House getHouse(long houseId);

    boolean isBoughtBySign(Location location);

    House getHouse(Location location);

    List<House> getAllHouses();

    long getHouseId(Location location);

    void registerNewHouse(House house);

}
