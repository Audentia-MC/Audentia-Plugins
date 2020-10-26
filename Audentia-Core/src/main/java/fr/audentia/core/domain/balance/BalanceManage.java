package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;

import java.util.HashMap;
import java.util.UUID;

public class BalanceManage {

    private final TeamsManager teamsManager;

    public BalanceManage(TeamsManager teamsManager) {
        this.teamsManager = teamsManager;
    }

    public String getBalanceWithMessage(UUID playerUUID) {

        Team team = this.teamsManager.getTeamOfPlayer(playerUUID);
        String message = "<error>Votre groupe ne possède pas de compte en banque.";

        Balance balance = team.balance;

        if (balance.hasBalance()) {
            message = "&" + ColorsUtils.fromColorToHexadecimal(team.color) + "Balance : " + balance.toString() + " émeraudes.";
        }

        return message;
    }

    public String getBalance(UUID playerUUID) {

        Team team = this.teamsManager.getTeamOfPlayer(playerUUID);
        return team.balance.toString();
    }

    public String addToBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeamOfPlayer(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return "<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.";
        }

        team = new Team(team.color, balance.add(count), team.transfers, team.name, team.houseId);
        teamsManager.saveTeam(team);
        return "<success>Dépôt effectué.";
    }

    public String removeFromBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeamOfPlayer(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return "<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.";
        }

        team = new Team(team.color, balance.remove(count), team.transfers, team.name, team.houseId);
        teamsManager.saveTeam(team);
        return "<success>Retrait effectué.";
    }

    public void forceAddToBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeamOfPlayer(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return;
        }

        team = new Team(team.color, balance.add(count), team.transfers, team.name, team.houseId);
        teamsManager.saveTeam(team);
    }

    public void forceRemoveFromBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeamOfPlayer(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return;
        }

        team = new Team(team.color, balance.remove(count), team.transfers, team.name, team.houseId);
        teamsManager.saveTeam(team);
    }

}
