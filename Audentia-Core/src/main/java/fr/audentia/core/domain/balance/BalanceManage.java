package fr.audentia.core.domain.balance;

import fr.audentia.core.domain.model.balance.BankTransfer;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;

import java.time.ZonedDateTime;
import java.util.UUID;

public class BalanceManage {

    private final TeamsManager teamsManager;
    private final TransfersRepository transfersRepository;

    public BalanceManage(TeamsManager teamsManager, TransfersRepository transfersRepository) {
        this.teamsManager = teamsManager;
        this.transfersRepository = transfersRepository;
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

    public String addToBalance(UUID playerUUID, int amount) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return "<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.";
        }

        transfersRepository.registerTransfer(team.color, new BankTransfer(ZonedDateTime.now().toLocalDateTime(), amount));

        team = new Team(team.color, balance.add(amount), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.save(team);
        return "<success>Dépôt effectué.";
    }

    public String removeFromBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return "<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.";
        }

        team = new Team(team.color, balance.remove(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.save(team);
        return "<success>Retrait effectué.";
    }

    public void forceAddToBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return;
        }

        team = new Team(team.color, balance.add(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.save(team);
    }

    public void forceRemoveFromBalance(UUID playerUUID, int count) {

        Team team = teamsManager.getTeam(playerUUID);
        Balance balance = team.balance;

        if (!balance.hasBalance()) {
            return;
        }

        team = new Team(team.color, balance.remove(count), team.transfers, team.coliseumKills, team.name, team.houseId);
        teamsManager.save(team);
    }

}
