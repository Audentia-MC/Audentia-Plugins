package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.staff.ban.BanAction;
import fr.audentia.core.domain.staff.grade.GradeInventoryAction;
import fr.audentia.core.domain.staff.inventory.LookInventoryAction;
import fr.audentia.core.domain.staff.kick.KickAction;
import fr.audentia.core.domain.staff.teleport.TeleportAction;
import fr.audentia.players.utils.ChatUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

import static fr.audentia.players.utils.ItemStackBuilder.anItemStack;

public class StaffInventory implements InventoryProvider {

    private final BanAction banAction;
    private final KickAction kickAction;
    private final TeleportAction teleportAction;
    private final LookInventoryAction lookInventoryAction;
    private final GradeInventoryAction gradeInventoryAction;
    private final UUID targetUUID;

    public StaffInventory(BanAction banAction,
                          KickAction kickAction,
                          TeleportAction teleportAction,
                          LookInventoryAction lookInventoryAction,
                          GradeInventoryAction gradeInventoryAction, UUID targetUUID) {

        this.banAction = banAction;
        this.kickAction = kickAction;
        this.teleportAction = teleportAction;
        this.lookInventoryAction = lookInventoryAction;
        this.gradeInventoryAction = gradeInventoryAction;
        this.targetUUID = targetUUID;
    }

    public static void openInventory(UUID staffUUID,
                                     UUID targetUUID,
                                     BanAction banAction,
                                     KickAction kickAction,
                                     TeleportAction teleportAction,
                                     LookInventoryAction lookInventoryAction,
                                     GradeInventoryAction gradeInventoryAction) {

        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            return;
        }

        SmartInventory.builder()
                .id("staff")
                .title("Menu de gestion : " + target.getName())
                .provider(new StaffInventory(banAction, kickAction, teleportAction, lookInventoryAction, gradeInventoryAction, targetUUID))
                .closeable(true)
                .size(3, 9)
                .build()
                .open(Bukkit.getPlayer(staffUUID));
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(1, 2), ClickableItem.of(
                anItemStack()
                        .withName("Ban")
                        .withMaterial(Material.BEDROCK)
                        .withAmount(1)
                        .addLore("Bannir le joueur")
                        .build(),
                event -> BanInventory.open(banAction, targetUUID, player)
        ));

        contents.set(new SlotPos(1, 3), ClickableItem.of(
                anItemStack()
                        .withName("Kick")
                        .withMaterial(Material.DARK_OAK_DOOR)
                        .withAmount(1)
                        .addLore("Kick le joueur")
                        .build(),
                event -> KickInventory.open(kickAction, targetUUID, player)
        ));

        contents.set(new SlotPos(1, 4), ClickableItem.of(
                anItemStack()
                        .withName("Grade")
                        .withMaterial(Material.LEATHER_CHESTPLATE)
                        .withAmount(1)
                        .addLore("Gérer le grade du joueur")
                        .build(),
                event -> {
                    String result = gradeInventoryAction.openGradeInventory(player.getUniqueId(), targetUUID);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 5), ClickableItem.of(
                anItemStack()
                        .withName("Teleport")
                        .withMaterial(Material.ENDER_EYE)
                        .withAmount(1)
                        .addLore("Se téléporter au joueur")
                        .build(),
                event -> {
                    String result = teleportAction.teleport(player.getUniqueId(), targetUUID);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                    if (result.startsWith("<success>")) player.closeInventory();
                }
        ));

        contents.set(new SlotPos(1, 6), ClickableItem.of(
                anItemStack()
                        .withName("Look inventory")
                        .withMaterial(Material.BOOK)
                        .withAmount(1)
                        .addLore("Ouvrir l'inventaire du joueur")
                        .build(),
                event -> {
                    String result = lookInventoryAction.lookInventory(player.getUniqueId(), targetUUID);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

}
