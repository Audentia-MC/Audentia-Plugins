package fr.audentia.core.domain.balance;

import fr.audentia.core.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;

import java.util.UUID;

public class BalanceManage {

    private final TeamsManager teamsManager;
    private final BalanceRepository balanceRepository;

    public BalanceManage(TeamsManager teamsManager, BalanceRepository balanceRepository) {
        this.teamsManager = teamsManager;
        this.balanceRepository = balanceRepository;
    }

    public String getBalanceWithMessage(UUID playerUUID) {

        Team team = this.teamsManager.getTeamOfPlayer(playerUUID);
        String message = "<error>Votre groupe ne possède pas de compte en banque.";

        Balance balance = this.balanceRepository.getTeamBalance(team);

        if (balance.hasBalance()) {
            message = "&" + ColorsUtils.fromColorToHexadecimal(team.color) + "Balance : " + balance.toString() + " émeraudes.";
        }

        return message;
    }

    public String getBalance(UUID playerUUID) {

        Team team = this.teamsManager.getTeamOfPlayer(playerUUID);
        return this.balanceRepository.getTeamBalance(team).toString();
    }

}
