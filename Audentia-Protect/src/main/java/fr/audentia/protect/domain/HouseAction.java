package fr.audentia.protect.domain;

import fr.audentia.protect.domain.model.Location;

public class HouseAction {

    private final HouseRepository houseRepository;

    public HouseAction(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public boolean breakSign(Location location) {

        return houseRepository.isRegisteredSign(location);
    }

}
