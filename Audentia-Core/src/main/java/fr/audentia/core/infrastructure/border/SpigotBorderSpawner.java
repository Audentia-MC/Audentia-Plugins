package fr.audentia.core.infrastructure.border;

import fr.audentia.core.domain.border.BorderSpawner;
import fr.audentia.core.domain.model.border.BorderLocation;
import fr.audentia.core.domain.model.border.BorderSize;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class SpigotBorderSpawner implements BorderSpawner {

    @Override
    public void spawnBorder(BorderLocation borderLocation, BorderSize borderSize) {

        World world = Bukkit.getWorld("world");

        if (world == null) {
            return;
        }

        world.getWorldBorder().setCenter(borderLocation.x, borderLocation.z);
        world.getWorldBorder().setSize(borderSize.size);
    }

}
