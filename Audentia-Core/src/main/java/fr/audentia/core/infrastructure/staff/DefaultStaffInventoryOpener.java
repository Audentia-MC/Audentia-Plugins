package fr.audentia.core.infrastructure.staff;

import fr.audentia.core.application.inventories.StaffInventory;
import fr.audentia.core.domain.staff.StaffInventoryOpener;
import fr.audentia.core.domain.staff.ban.BanAction;
import fr.audentia.core.domain.staff.grade.GradeInventoryAction;
import fr.audentia.core.domain.staff.inventory.LookInventoryAction;
import fr.audentia.core.domain.staff.kick.KickAction;
import fr.audentia.core.domain.staff.teleport.TeleportAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DefaultStaffInventoryOpener implements StaffInventoryOpener {

    private final BanAction banAction;
    private final KickAction kickAction;
    private final TeleportAction teleportAction;
    private final LookInventoryAction lookInventoryAction;
    private final GradeInventoryAction gradeInventoryAction;

    public DefaultStaffInventoryOpener(BanAction banAction,
                                       KickAction kickAction,
                                       TeleportAction teleportAction,
                                       LookInventoryAction lookInventoryAction,
                                       GradeInventoryAction gradeInventoryAction) {

        this.banAction = banAction;
        this.kickAction = kickAction;
        this.teleportAction = teleportAction;
        this.lookInventoryAction = lookInventoryAction;
        this.gradeInventoryAction = gradeInventoryAction;
    }

    @Override
    public void openInventory(UUID staffUUID, String targetName) {

        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            return;
        }

        StaffInventory.openInventory(staffUUID, target.getUniqueId(), banAction, kickAction, teleportAction, lookInventoryAction, gradeInventoryAction);
    }

}
