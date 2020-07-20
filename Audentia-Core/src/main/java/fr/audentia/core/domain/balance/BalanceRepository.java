package fr.audentia.core.domain.balance;

import fr.audentia.core.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;

import java.util.OptionalInt;

public interface BalanceRepository {

    Balance getTeamBalance(Team team);

}
