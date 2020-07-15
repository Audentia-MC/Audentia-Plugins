package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.teams.Team;

import java.util.OptionalInt;

public interface BalanceRepository {

    OptionalInt getTeamBalance(Team team);

}
