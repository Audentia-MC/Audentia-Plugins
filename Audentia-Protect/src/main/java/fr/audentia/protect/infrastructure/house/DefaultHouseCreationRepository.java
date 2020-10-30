package fr.audentia.protect.infrastructure.house;

import fr.audentia.protect.domain.house.HouseCreationRepository;
import fr.audentia.protect.domain.model.house.HouseCreationModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultHouseCreationRepository implements HouseCreationRepository {

    private final Map<UUID, HouseCreationModel> runningCreations = new HashMap<>();

    @Override
    public void startCreation(UUID playerUUID) {
        this.runningCreations.put(playerUUID, new HouseCreationModel());
    }

    @Override
    public void stopCreation(UUID playerUUID) {
        this.runningCreations.remove(playerUUID);
    }

    @Override
    public boolean isRunningCreation(UUID playerUUID) {
        return this.runningCreations.containsKey(playerUUID);
    }

    @Override
    public HouseCreationModel getCreation(UUID playerUUID) {

        if (!this.runningCreations.containsKey(playerUUID)) {
            return new HouseCreationModel();
        }

        return this.runningCreations.get(playerUUID);
    }

    @Override
    public void saveCreation(UUID playerUUID, HouseCreationModel creation) {
        this.runningCreations.put(playerUUID, creation);
    }

}
