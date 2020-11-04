package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;

import java.awt.*;
import java.util.UUID;

public class BankManage {

    private final BalanceManage balanceManage;
    private final GamesInfosRepository gamesInfosRepository;
    private final TeamsManager teamsManager;
    private final InventoryUtilities inventoryUtilities;

    public BankManage(BalanceManage balanceManage, GamesInfosRepository gamesInfosRepository, TeamsManager teamsManager, InventoryUtilities inventoryUtilities) {
        this.balanceManage = balanceManage;
        this.gamesInfosRepository = gamesInfosRepository;
        this.teamsManager = teamsManager;
        this.inventoryUtilities = inventoryUtilities;
    }

    public String depositEmeralds(UUID playerUUID, int count) {

        Day day = gamesInfosRepository.getDay();
        Team team = teamsManager.getTeam(playerUUID);

        if (team.color == Color.BLACK) {
            return "<error>Votre groupe ne peut pas accéder à la banque.";
        }

        if (!team.transfers.containsKey(day)) {
            team.transfers.put(day, new DayTransfers(0));
        }

        int finalCount = gamesInfosRepository.getEmeraldsLimitation(day).computePossibleTransfer(count, team.transfers.get(day));

        if (finalCount == -1) {
            return "<error>Vous avez déjà déposé le maximum d'émeraudes possible pour aujourd'hui.";
        }

        String result = balanceManage.addToBalance(playerUUID, finalCount);

        if (result.startsWith("<success>")) {
            inventoryUtilities.removeEmeralds(playerUUID, finalCount);
        }

        return result;
    }

    public String removeEmeralds(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);

        if (team.color == Color.BLACK) {
            return "<error>Votre groupe ne peut pas accéder à la banque.";
        }

        return balanceManage.removeFromBalance(playerUUID, count);
    }

}
