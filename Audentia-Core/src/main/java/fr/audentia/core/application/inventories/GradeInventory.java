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
                .size(6, 9)
                .build()
                .open(Bukkit.getPlayer(staffUUID));
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(3, 2), ClickableItem.of(
                anItemStack()
                        .withName("Empereur")
                        .withMaterial(getMaterial(0))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 0);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(3, 3), ClickableItem.of(
                anItemStack()
                        .withName("Ingénieur")
                        .withMaterial(getMaterial(1))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 1);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(3, 5), ClickableItem.of(
                anItemStack()
                        .withName("Modérateur")
                        .withMaterial(getMaterial(2))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 2);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(3, 6), ClickableItem.of(
                anItemStack()
                        .withName("Architecte")
                        .withMaterial(getMaterial(3))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 3);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(4, 2), ClickableItem.of(
                anItemStack()
                        .withName("Consul")
                        .withMaterial(getMaterial(4))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 4);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(4, 3), ClickableItem.of(
                anItemStack()
                        .withName("Préfet")
                        .withMaterial(getMaterial(5))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 5);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(4, 4), ClickableItem.of(
                anItemStack()
                        .withName("Légat")
                        .withMaterial(getMaterial(5))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 6);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(4, 5), ClickableItem.of(
                anItemStack()
                        .withName("Tribun")
                        .withMaterial(getMaterial(5))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 7);
                    player.sendMessage(ChatUtils.format(result));
                }
        ));

        contents.set(new SlotPos(4, 6), ClickableItem.of(
                anItemStack()
                        .withName("Centurion")
                        .withMaterial(getMaterial(5))
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = gradeChangeAction.changeGrade(player.getUniqueId(), targetUUID, 8);
                    player.sendMessage(ChatUtils.format(result));
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

        return getMaterial(rolesRepository.getRole(targetUUID).number);

    }

    private Material getMaterial(int number) {

        switch (number) {

            case 0:
                return Material.TRIDENT;

            case 1:
                return Material.REDSTONE_WIRE;

            case 2:
                return Material.DIAMOND_SWORD;

            case 3:
                return Material.DIAMOND_PICKAXE;

            case 4:
                return Material.DIAMOND_CHESTPLATE;

            case 5:
                return Material.GOLDEN_CHESTPLATE;

            case 6:
                return Material.IRON_CHESTPLATE;

            case 7:
                return Material.CHAINMAIL_CHESTPLATE;

            default:
                return Material.LEATHER_CHESTPLATE;

        }

    }

}
