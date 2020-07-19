package fr.audentia.core.main;

import fr.audentia.core.domain.balance.BalanceManager;
import fr.audentia.core.domain.balance.BalanceRepository;
import fr.audentia.core.infrastructure.balance.MariaDbBalanceRepository;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;

public class AudentiaCoreManagersProvider {

    private final BalanceRepository BALANCE_REPOSITORY;

    public final BalanceManager BALANCE_MANAGER;

    public AudentiaCoreManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider) {

        BALANCE_REPOSITORY = new MariaDbBalanceRepository();

        this.BALANCE_MANAGER = new BalanceManager(audentiaPlayersManagersProvider.TEAMS_MANAGER, BALANCE_REPOSITORY);
    }

}
