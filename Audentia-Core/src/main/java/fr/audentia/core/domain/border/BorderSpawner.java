package fr.audentia.core.domain.border;

import fr.audentia.core.domain.model.border.BorderLocation;
import fr.audentia.core.domain.model.border.BorderSize;

public interface BorderSpawner {

    void spawnBorder(BorderLocation borderLocation, BorderSize borderSize);

}
