package fr.audentia.core.infrastructure.repositories.balance;

import fr.audentia.core.domain.balance.BalanceRepository;
import fr.audentia.players.domain.teams.Team;

import java.util.OptionalDouble;

public class MariaDbBalanceRepository implements BalanceRepository {

    @Override
    public OptionalDouble getTeamBalance(Team team) {
        return OptionalDouble.empty();
    }

}
