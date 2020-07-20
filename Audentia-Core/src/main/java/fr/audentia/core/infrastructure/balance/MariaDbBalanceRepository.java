package fr.audentia.core.infrastructure.balance;

import fr.audentia.core.domain.balance.BalanceRepository;
import fr.audentia.core.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;

public class MariaDbBalanceRepository implements BalanceRepository {

    @Override
    public Balance getTeamBalance(Team team) {
        return new Balance(0);
    }

}
