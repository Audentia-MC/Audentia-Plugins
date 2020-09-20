package fr.audentia.core.infrastructure.staff.grade;

import fr.audentia.core.application.inventories.GradeInventory;
import fr.audentia.core.domain.staff.grade.GradeChangeAction;
import fr.audentia.core.domain.staff.grade.GradeInventoryOpener;
import fr.audentia.players.domain.teams.RolesRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DefaultGradeInventoryOpener implements GradeInventoryOpener {

    private final GradeChangeAction gradeChangeAction;
    private final RolesRepository rolesRepository;

    public DefaultGradeInventoryOpener(GradeChangeAction gradeChangeAction, RolesRepository rolesRepository) {
        this.gradeChangeAction = gradeChangeAction;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public void openInventory(UUID staffUUID, UUID targetUUID) {

        Player target = Bukkit.getPlayer(targetUUID);

        if (target == null) {
            return;
        }

        GradeInventory.openInventory(staffUUID, targetUUID, gradeChangeAction, rolesRepository);
    }

}
