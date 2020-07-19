package fr.audentia.core.main;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.balance.BalanceRepository;
import fr.audentia.core.infrastructure.balance.MariaDbBalanceRepository;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;

public class AudentiaCoreManagersProvider {

    private final BalanceRepository BALANCE_REPOSITORY;

    public final BalanceManage BALANCE_MANAGER;

    public AudentiaCoreManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider) {

        BALANCE_REPOSITORY = new MariaDbBalanceRepository();

        this.BALANCE_MANAGER = new BalanceManage(audentiaPlayersManagersProvider.TEAMS_MANAGER, BALANCE_REPOSITORY);
    }

}
