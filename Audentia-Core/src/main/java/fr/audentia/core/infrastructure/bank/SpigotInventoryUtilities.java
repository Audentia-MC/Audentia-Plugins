package fr.audentia.core.infrastructure.bank;

import fr.audentia.core.domain.bank.InventoryUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static fr.audentia.players.utils.ItemStackBuilder.anItemStack;

public class SpigotInventoryUtilities implements InventoryUtilities {

    @Override
    public boolean hasEmeralds(UUID playerUUID, int count) {

        return hasItem(playerUUID, "EMERALD", count);
    }

    @Override
    public boolean hasItem(UUID playerUUID, String materialName, int count) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return false;
        }

        Material material = Material.getMaterial(materialName);

        if (material == null) {
            return false;
        }

        return Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(item -> item.getType() == material)
                .filter(itemStack -> !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName())
                .map(ItemStack::getAmount)
                .reduce((sum, amount) -> sum += amount)
                .orElse(0) >= count;
    }

    @Override
    public void removeEmeralds(UUID playerUUID, int count) {

        removeItems(playerUUID, "EMERALD", count);
    }

    @Override
    public void removeItems(UUID playerUUID, String materialName, int count) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        Material material = Material.getMaterial(materialName);

        if (material == null) {
            return;
        }

        for (ItemStack content : player.getInventory().getContents()) {

            if (count <= 0) {
                break;
            }

            if (content == null || content.getType() != material) {
                continue;
            }

            int amount = content.getAmount();
            content.setAmount(amount - count);
            count -= amount;

        }

    }

    @Override
    public void addItem(UUID playerUUID, String materialName, int count) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        Material material = Material.getMaterial(materialName);

        if (material == null) {
            return;
        }

        player.getInventory().addItem(anItemStack()
                .withMaterial(material)
                .withAmount(count)
                .buildSimple());
    }

    @Override
    public void clearInventory(UUID playerUUID) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        player.getInventory().clear();
    }

}
