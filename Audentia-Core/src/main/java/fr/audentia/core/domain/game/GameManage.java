package fr.audentia.core.domain.game;

import fr.audentia.core.domain.bank.InventoryUtilities;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.util.List;
import java.util.UUID;

public class GameManage {

    private final RolesRepository rolesRepository;
    private final PlayerGameModeManage playerGameModeManage;
    private final PlayerFinder playerFinder;
    private final TeamsManager teamsManager;
    private final InventoryUtilities inventoryUtilities;
    private final GamesInfosRepository gamesInfosRepository;
    private final PlayerMessageSender playerMessageSender;

    public GameManage(RolesRepository rolesRepository, PlayerGameModeManage playerGameModeManage, PlayerFinder playerFinder, TeamsManager teamsManager, InventoryUtilities inventoryUtilities, GamesInfosRepository gamesInfosRepository, PlayerMessageSender playerMessageSender) {
        this.rolesRepository = rolesRepository;
        this.playerGameModeManage = playerGameModeManage;
        this.playerFinder = playerFinder;
        this.teamsManager = teamsManager;
        this.inventoryUtilities = inventoryUtilities;
        this.gamesInfosRepository = gamesInfosRepository;
        this.playerMessageSender = playerMessageSender;
    }

    public String startGame(UUID playerUUID, String days) {

        Role role = rolesRepository.getRole(playerUUID);

        if (role.number > 1) {
            return "<error>Vous n'avez pas le pouvoir de lancer une partie.";
        }

        if (!days.matches("[0-9]+")) {
            return "<error>/start <nombre de jour de la partie>";
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

        long startInSeconds = System.currentTimeMillis() / 1000;
        int durationInSeconds = Integer.parseInt(days) * 24 * 60 * 60;
        gamesInfosRepository.addEntry(startInSeconds, durationInSeconds);
        return "<success>Partie lancée.";
    }

    public void checkEndGame() {

        GameState gameState = gamesInfosRepository.getGameState();

        if (gameState != GameState.PLAYING) {
            return;
        }

        long startTimeInSeconds = gamesInfosRepository.getStartTimeInSeconds();
        long gameDurationInSeconds = gamesInfosRepository.getGameDurationInSeconds();

        if (System.currentTimeMillis() / 1000 < startTimeInSeconds + gameDurationInSeconds) {
            return;
        }

        gamesInfosRepository.setState(GameState.ENDED);

        List<UUID> players = playerFinder.getAllPlayers();

        for (UUID player : players) {
            playerMessageSender.sendMessage(player, "<success>La partie est terminée, bravo à tous !");
        }
    }

}
