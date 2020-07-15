package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.teams.Team;

import java.util.OptionalDouble;

public interface BalanceRepository {

    OptionalDouble getTeamBalance(Team team);

}
