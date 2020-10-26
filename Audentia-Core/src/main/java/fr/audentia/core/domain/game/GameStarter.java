package fr.audentia.core.domain.game;

import fr.audentia.core.domain.bank.InventoryUtilities;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.util.List;
import java.util.UUID;

public class GameStarter {

    private final RolesRepository rolesRepository;
    private final PlayerGameModeManage playerGameModeManage;
    private final PlayerFinder playerFinder;
    private final TeamsManager teamsManager;
    private final InventoryUtilities inventoryUtilities;
    private final GamesInfosRepository gamesInfosRepository;
    private final PlayerMessageSender playerMessageSender;

    public GameStarter(RolesRepository rolesRepository, PlayerGameModeManage playerGameModeManage, PlayerFinder playerFinder, TeamsManager teamsManager, InventoryUtilities inventoryUtilities, GamesInfosRepository gamesInfosRepository, PlayerMessageSender playerMessageSender) {
        this.rolesRepository = rolesRepository;
        this.playerGameModeManage = playerGameModeManage;
        this.playerFinder = playerFinder;
        this.teamsManager = teamsManager;
        this.inventoryUtilities = inventoryUtilities;
        this.gamesInfosRepository = gamesInfosRepository;
        this.playerMessageSender = playerMessageSender;
    }

    public String startGame(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.number > 1) {
            return "<error>Vous n'avez pas le pouvoir de lancer une partie.";
        }

        List<UUID> players = playerFinder.getAllPlayers();

        for (UUID player : players) {

            role = rolesRepository.getRole(player);
            playerMessageSender.sendMessage(player, "<success>La partie se lance, bonne chance !");

            if (role.staff) {
                continue;
            }

            playerGameModeManage.changeGameModeToSurvival(player);
            teamsManager.resetTeam(player);
            inventoryUtilities.clearInventory(player);
        }

        gamesInfosRepository.setDay(1);
        gamesInfosRepository.setStart(System.currentTimeMillis() / 1000);
        return "<success>Partie lancée.";
    }

}