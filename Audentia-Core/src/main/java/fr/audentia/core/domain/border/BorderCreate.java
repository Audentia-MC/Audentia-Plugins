package fr.audentia.core.domain.border;

import fr.audentia.core.domain.model.border.BorderLocation;
import fr.audentia.core.domain.model.border.BorderSize;

public class BorderCreate {

    private final BorderInfosRepository borderInfosRepository;
    private final BorderSpawner borderSpawner;

    public BorderCreate(BorderInfosRepository borderInfosRepository, BorderSpawner borderSpawner) {
        this.borderInfosRepository = borderInfosRepository;
        this.borderSpawner = borderSpawner;
    }

    public void createWorldBorder() {

        BorderSize size = borderInfosRepository.getBorderSize();

        if (size.size == 0) {
            return;
        }

        BorderLocation location = borderInfosRepository.getBorderLocation();
        borderSpawner.spawnBorder(location, size);
    }

}
