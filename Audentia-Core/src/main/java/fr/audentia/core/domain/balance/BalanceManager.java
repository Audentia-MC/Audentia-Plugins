package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;

import java.util.OptionalDouble;
import java.util.UUID;

public class BalanceManager {

    private final TeamsManager teamsManager;
    private final BalanceRepository balanceRepository;

    public BalanceManager(TeamsManager teamsManager, BalanceRepository balanceRepository) {
        this.teamsManager = teamsManager;
        this.balanceRepository = balanceRepository;
    }


    public String getBalanceOfPlayer(UUID playerUUID) {

        Team team = this.teamsManager.getTeamOfPlayer(playerUUID);

        String message = "<error>Votre groupe ne possède pas de compte en banque.";

        OptionalDouble balance = this.balanceRepository.getTeamBalance(team);

        if (balance.isPresent()) {

            message = "&#" + team.color + "Balance : " + balance.getAsDouble() + ".";

        }

        return message;
    }

}
