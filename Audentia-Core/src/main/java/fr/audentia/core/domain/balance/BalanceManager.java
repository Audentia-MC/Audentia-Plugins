package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;

import java.util.OptionalInt;
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

        OptionalInt balance = this.balanceRepository.getTeamBalance(team);

        if (balance.isPresent()) {

            message = "&" + ColorsUtils.fromColorToHexadecimal(team.color) + "Balance : " + balance.getAsInt() + " émeraudes.";

        }

        return message;
    }

}
