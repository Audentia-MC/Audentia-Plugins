package fr.audentia.protect.domain;

import fr.audentia.protect.domain.model.Location;

import java.util.UUID;

public class BuyHouseAction {

    private final BuyHouseClicksRepository buyHouseClicksRepository;

    public BuyHouseAction(BuyHouseClicksRepository buyHouseClicksRepository) {
        this.buyHouseClicksRepository = buyHouseClicksRepository;
    }

    public boolean isSecondClick(UUID playerUUID, Location location) {

        if (!this.buyHouseClicksRepository.isRegistered(playerUUID, location)) {
            this.buyHouseClicksRepository.register(playerUUID, location);
            return false;
        }

        return true;
    }

    public void clearOld() {
        this.buyHouseClicksRepository.clearOld();
    }

}
