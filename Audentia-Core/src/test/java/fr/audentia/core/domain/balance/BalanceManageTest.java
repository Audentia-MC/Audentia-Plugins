package fr.audentia.core.domain.balance;

import fr.audentia.core.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
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
    public void getBalanceWithMessage_shouldReturnSuccessMessage_whenPlayerTeamHasBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(new Balance(0));

        String balance = this.balanceManage.getBalanceWithMessage(PLAYER_UUID);

        assertThat(balance).isEqualTo("&#FF0000Balance : 0 émeraudes.");
    }

    @Test
    public void getBalanceWithMessage_shouldReturnErrorMessage_whenPlayerTeamHasNotBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(new Balance(-1));

        String balance = this.balanceManage.getBalanceWithMessage(PLAYER_UUID);

        assertThat(balance).isEqualTo("<error>Votre groupe ne possède pas de compte en banque.");
    }

    @Test
    public void getBalance_shouldReturn10_whenPlayerTeamHas10Emeralds() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(new Balance(10));

        String balance = this.balanceManage.getBalance(PLAYER_UUID);

        assertThat(balance).isEqualTo("10");
    }

    @Test
    public void getBalance_shouldReturnMinus1_whenPlayerTeamHasNotBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(new Balance(-1));

        String balance = this.balanceManage.getBalance(PLAYER_UUID);

        assertThat(balance).isEqualTo("-1");
    }

}