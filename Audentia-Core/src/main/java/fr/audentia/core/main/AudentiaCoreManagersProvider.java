package fr.audentia.core.main;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;

public class AudentiaCoreManagersProvider {

    public final BalanceManage BALANCE_MANAGER;

    public AudentiaCoreManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider) {

        this.BALANCE_MANAGER = new BalanceManage(audentiaPlayersManagersProvider.TEAMS_MANAGER);
    }

}
