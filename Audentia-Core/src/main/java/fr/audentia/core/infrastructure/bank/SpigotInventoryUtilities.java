package fr.audentia.core.infrastructure.bank;

import fr.audentia.core.domain.bank.InventoryUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class SpigotInventoryUtilities implements InventoryUtilities {

    @Override
    public boolean hasEmeralds(UUID playerUUID, int count) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return false;
        }

        return Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(item -> item.getType() == Material.EMERALD)
                .map(ItemStack::getAmount)
                .reduce((sum, amount) -> sum += amount)
                .orElse(0)>= count;
    }

    @Override
    public void removeEmeralds(UUID playerUUID, int count) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        for (ItemStack content : player.getInventory().getContents()) {

            if (count <= 0) {
                break;
            }

            if (content.getType() != Material.EMERALD) {
                continue;
            }

            int amount = content.getAmount();
            content.setAmount(amount - count);
            count -= amount;

        }

    }

}
