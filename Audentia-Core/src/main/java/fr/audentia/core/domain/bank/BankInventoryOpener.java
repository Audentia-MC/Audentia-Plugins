package fr.audentia.core.domain.bank;

import java.awt.*;
import java.util.UUID;

public interface BankInventoryOpener {

    void open(UUID playerUUID, Color teamColor);

}
