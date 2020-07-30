package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;
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

        team = new Team(team.color, balance.add(count), new HashMap<>(), "Tony");
        teamsManager.saveTeam(team);
        return "<success>Dépôt effectué.";
    }

}