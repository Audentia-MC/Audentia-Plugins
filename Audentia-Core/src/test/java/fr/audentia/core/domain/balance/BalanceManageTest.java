package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.OptionalInt;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BalanceManageTest {

    private static final UUID PLAYER_UUID = UUID.randomUUID();
    private static final Team FAKE_TEAM = new Team(Color.RED);

    private BalanceRepository balanceRepository;
    private BalanceManage balanceManage;

    @Before
    public void setUp() {
        TeamsManager teamsManager = Mockito.mock(TeamsManager.class);
        this.balanceRepository = Mockito.mock(BalanceRepository.class);
        this.balanceManage = new BalanceManage(teamsManager, balanceRepository);

        when(teamsManager.getTeamOfPlayer(PLAYER_UUID)).thenReturn(FAKE_TEAM);
    }

    @Test
    public void getBalanceOfPlayer_shouldReturnSuccessMessage_whenPlayerTeamHasBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(OptionalInt.of(0));

        String balance = this.balanceManage.getBalanceOfPlayer(PLAYER_UUID);

        assertThat(balance).isEqualTo("&#FF0000Balance : 0 émeraudes.");
    }

    @Test
    public void getBalanceOfPlayer_shouldReturnErrorMessage_whenPlayerTeamHasNotBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(OptionalInt.empty());

        String balance = this.balanceManage.getBalanceOfPlayer(PLAYER_UUID);

        assertThat(balance).isEqualTo("<error>Votre groupe ne possède pas de compte en banque.");
    }

}