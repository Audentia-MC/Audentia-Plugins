package fr.audentia.core.domain.balance;

import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;

import java.util.UUID;

public class BalanceManage {

    private final TeamsManager teamsManager;
    private final GamesInfosRepository gamesInfosRepository;

    public BalanceManage(TeamsManager teamsManager, GamesInfosRepository gamesInfosRepository) {
        this.teamsManager = teamsManager;
        this.gamesInfosRepository = gamesInfosRepository;
    }

    public String getBalanceWithMessage(UUID playerUUID) {

        Team team = this.teamsManager.getTeam(playerUUID);
        String message = "<error>Votre groupe ne possède pas de compte en banque.";

        Balance balance = team.balance;

        if (balance.hasBalance()) {
            message = "&" + ColorsUtils.fromColorToHexadecimal(team.color) + "Balance : " + balance.toString() + " émeraudes.";
        }

        return message;
    }

    public String getBalance(UUID playerUUID) {

        Team team = this.teamsManager.getTeam(playerUUID);
        return team.balance.toString();
    }

    public String addToBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return "<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.";
        }

        Day day = gamesInfosRepository.getDay();

        if (!team.transfers.containsKey(day)) {
            team.transfers.put(day, new DayTransfers(0));
        }

        DayTransfers add = team.transfers.get(day).add(count);
        team.transfers.put(day, add);

        team = new Team(team.color, balance.add(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.saveTeam(team);
        return "<success>Dépôt effectué.";
    }

    public String removeFromBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return "<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.";
        }

        team = new Team(team.color, balance.remove(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.saveTeam(team);
        return "<success>Retrait effectué.";
    }

    public void forceAddToBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return;
        }

        team = new Team(team.color, balance.add(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.saveTeam(team);
    }

    public void forceRemoveFromBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return;
        }

        team = new Team(team.color, balance.remove(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.saveTeam(team);
    }

}
