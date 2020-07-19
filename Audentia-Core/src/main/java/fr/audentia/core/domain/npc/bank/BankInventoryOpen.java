package fr.audentia.core.domain.npc.bank;

import fr.audentia.players.domain.teams.TeamsManager;

import java.util.UUID;

public class BankInventoryOpen {

    private final TeamsManager teamsManager;
    private final BankInventory bankInventory;

    public BankInventoryOpen(TeamsManager teamsManager, BankInventory bankInventory) {
        this.teamsManager = teamsManager;
        this.bankInventory = bankInventory;
    }

    public String openInventory(UUID playerUUID) {

        String result = "<error>Votre groupe ne peut pas accéder à la banque.";

        if (teamsManager.isPlayer(playerUUID)) {
            result = "<success>Bienvenue dans la banque.";
            bankInventory.open(playerUUID, teamsManager.getTeamOfPlayer(playerUUID).color);
        }

        if (teamsManager.isStaff(playerUUID)) {
            result = "<success>Bienvenue dans la banque fictive.";
            bankInventory.open(playerUUID, teamsManager.getTeamOfPlayer(playerUUID).color);

        }

        return result;
    }

}
