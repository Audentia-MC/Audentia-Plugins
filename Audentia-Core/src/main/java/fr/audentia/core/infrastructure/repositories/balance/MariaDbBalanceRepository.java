package fr.audentia.core.infrastructure.repositories.balance;

import fr.audentia.core.domain.balance.BalanceRepository;
import fr.audentia.players.domain.teams.Team;

import java.util.OptionalInt;

public class MariaDbBalanceRepository implements BalanceRepository {

    @Override
    public OptionalInt getTeamBalance(Team team) {
        return OptionalInt.empty();
    }

}
