package fr.audentia.protect.domain.house;

import fr.audentia.protect.domain.model.house.HouseCreationModel;

import java.util.UUID;

public interface HouseCreationRepository {

    void startCreation(UUID playerUUID);

    void stopCreation(UUID playerUUID);

    boolean isRunningCreation(UUID playerUUID);

    HouseCreationModel getCreation(UUID playerUUID);

    void saveCreation(UUID playerUUID, HouseCreationModel creation);

}
