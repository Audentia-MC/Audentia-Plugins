package fr.audentia.core.domain.staff.grade;

import java.util.UUID;

public interface GradeInventoryOpener {

    void openInventory(UUID staffUUID, UUID targetUUID);

}
