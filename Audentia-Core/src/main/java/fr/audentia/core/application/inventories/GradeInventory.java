package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.staff.grade.GradeChangeAction;
import fr.audentia.players.domain.teams.RolesRepository;
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

public class GradeInventory implements InventoryProvider {

    private final UUID targetUUID;
    private final GradeChangeAction gradeChangeAction;
    private final RolesRepository rolesRepository;

    public GradeInventory(UUID targetUUID, GradeChangeAction gradeChangeAction, RolesRepository rolesRepository) {
        this.targetUUID = targetUUID;
        this.gradeChangeAction = gradeChangeAction;
        this.rolesRepository = rolesRepository;
    }

    public static void openInventory(UUID staffUUID,
                                     UUID targetUUID,
                                     GradeChangeAction gradeChangeAction,
                                     RolesRepository rolesRepository) {

        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            return;
        }

        SmartInventory.builder()
                .id("staff")
                .title("Gestion du role : " + target.getName())
                .provider(new GradeInventory(targetUUID, gradeChangeAction, rolesRepository))
                .closeable(true)
                .size(5, 9)
                .build()
                .open(Bukkit.getPlayer(staffUUID));
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(2, 1), ClickableItem.of(
                anItemStack()
                        .withName("Empereur")
                        .withMaterial(getMaterial("Empereur"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 1);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(2, 2), ClickableItem.of(
                anItemStack()
                        .withName("Ingénieur")
                        .withMaterial(getMaterial("Ingénieur"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 2);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(2, 3), ClickableItem.of(
                anItemStack()
                        .withName("Préfet")
                        .withMaterial(getMaterial("Préfet"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 3);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(3, 1), ClickableItem.of(
                anItemStack()
                        .withName("Architecte")
                        .withMaterial(getMaterial("Architecte"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 4);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(3, 2), ClickableItem.of(
                anItemStack()
                        .withName("Artiste")
                        .withMaterial(getMaterial("Artiste"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 5);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(3, 3), ClickableItem.of(
                anItemStack()
                        .withName("Mécène")
                        .withMaterial(getMaterial("Mécène"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 6);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(2, 5), ClickableItem.of(
                anItemStack()
                        .withName("Consul")
                        .withMaterial(getMaterial("Consul"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 7);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(2, 6), ClickableItem.of(
                anItemStack()
                        .withName("Légat")
                        .withMaterial(getMaterial("Légat"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 8);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(2, 7), ClickableItem.of(
                anItemStack()
                        .withName("Tribun")
                        .withMaterial(getMaterial("Tribun"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 9);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(3, 6), ClickableItem.of(
                anItemStack()
                        .withName("Centurion")
                        .withMaterial(getMaterial("Centurion"))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 10);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

        contents.set(new SlotPos(1, 4), ClickableItem.empty(
                anItemStack()
                        .withName("Role actuel")
                        .withMaterial(getCurrentRoleMaterial())
                        .withAmount(1)
                        .addLore(getCurrentRoleName())
                        .build()
        ));

    }

    private String getCurrentRoleName() {

        return rolesRepository.getRole(targetUUID).name;
    }

    private Material getCurrentRoleMaterial() {

        return getMaterial(rolesRepository.getRole(targetUUID).name);
    }

    private Material getMaterial(String name) {

        switch (name.toLowerCase()) {

            case "empereur":
                return Material.TRIDENT;

            case "ingénieur":
                return Material.REDSTONE;

            case "préfet":
                return Material.DIAMOND_SWORD;

            case "architecte":
                return Material.DIAMOND_PICKAXE;

            case "artiste":
                return Material.PAINTING;

            case "mécène":
                return Material.GOLD_NUGGET;

            case "consul":
                return Material.DIAMOND_CHESTPLATE;

            case "légat":
                return Material.GOLDEN_CHESTPLATE;

            case "tribun":
                return Material.IRON_CHESTPLATE;

            default:
                return Material.LEATHER_CHESTPLATE;

        }

    }

}
