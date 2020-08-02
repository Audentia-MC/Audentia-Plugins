package fr.audentia.core.domain.bank;

import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.util.UUID;

public class BankInventoryOpen {

    private final TeamsManager teamsManager;
    private final BankInventoryOpener bankInventoryOpener;
    private final RolesRepository rolesRepository;

    public BankInventoryOpen(TeamsManager teamsManager, BankInventoryOpener bankInventoryOpener, RolesRepository rolesRepository) {
        this.teamsManager = teamsManager;
        this.bankInventoryOpener = bankInventoryOpener;
        this.rolesRepository = rolesRepository;
    }

    public String openInventory(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);
        String result = "<error>Votre groupe ne peut pas accéder à la banque.";

        if (role.player) {
            result = "<success>Bienvenue dans la banque.";
            bankInventoryOpener.open(playerUUID, teamsManager.getTeamOfPlayer(playerUUID).color);
        }

        if (role.staff) {
            result = "<success>Bienvenue dans la banque fictive.";
            bankInventoryOpener.open(playerUUID, teamsManager.getTeamOfPlayer(playerUUID).color);
        }

        return result;
    }

}
