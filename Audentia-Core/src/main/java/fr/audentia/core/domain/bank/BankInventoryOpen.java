package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.util.UUID;

public class BankInventoryOpen {

    private final TeamsManager teamsManager;
    private final BankInventoryOpener bankInventoryOpener;
    private final RolesRepository rolesRepository;
    private final GamesInfosRepository gamesInfosRepository;
    private final BankSlotsRepository bankSlotsRepository;
    private final TimeProvider timeProvider;

    public BankInventoryOpen(TeamsManager teamsManager, BankInventoryOpener bankInventoryOpener, RolesRepository rolesRepository, GamesInfosRepository gamesInfosRepository, BankSlotsRepository bankSlotsRepository, TimeProvider timeProvider) {
        this.teamsManager = teamsManager;
        this.bankInventoryOpener = bankInventoryOpener;
        this.rolesRepository = rolesRepository;
        this.gamesInfosRepository = gamesInfosRepository;
        this.bankSlotsRepository = bankSlotsRepository;
        this.timeProvider = timeProvider;
    }

    public String openInventory(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        Day day = gamesInfosRepository.getDay();

        if (!bankSlotsRepository.getBankOpenSlots(day).isOpen(timeProvider.getHour())) {
            return "<error>La banque est fermée.";
        }

        if (role.staff) {
            bankInventoryOpener.open(playerUUID, teamsManager.getTeam(playerUUID).color);
            return "<success>Bienvenue dans la banque fictive.";
        }

        if (role.player) {
            bankInventoryOpener.open(playerUUID, teamsManager.getTeam(playerUUID).color);
            return "<success>Bienvenue dans la banque.";
        }

        return "<error>Votre groupe ne peut pas accéder à la banque.";
    }

}
