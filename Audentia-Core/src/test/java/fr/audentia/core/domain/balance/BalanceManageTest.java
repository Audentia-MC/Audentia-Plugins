package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BalanceManageTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    private TeamsManager teamsManager;
    private BalanceManage balanceManage;

    @Before
    public void setUp() {
        this.teamsManager = Mockito.mock(TeamsManager.class);
        this.balanceManage = new BalanceManage(teamsManager);
    }

    @Test
    public void getBalanceWithMessage_shouldReturnSuccessMessage_whenPlayerTeamHasBalance() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalanceWithMessage(FAKE_UUID);

        assertThat(balance).isEqualTo("&#FF0000Balance : 0 émeraudes.");
    }

    @Test
    public void getBalanceWithMessage_shouldReturnErrorMessage_whenPlayerTeamHasNotBalance() {

        Team team = new Team(Color.RED, new Balance(-1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalanceWithMessage(FAKE_UUID);

        assertThat(balance).isEqualTo("<error>Votre groupe ne possède pas de compte en banque.");
    }

    @Test
    public void getBalance_shouldReturn10_whenPlayerTeamHas10Emeralds() {

        Team team = new Team(Color.RED, new Balance(10), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalance(FAKE_UUID);

        assertThat(balance).isEqualTo("10");
    }

    @Test
    public void getBalance_shouldReturnMinus1_whenPlayerTeamHasNotBalance() {

        Team team = new Team(Color.RED, new Balance(-1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalance(FAKE_UUID);

        assertThat(balance).isEqualTo("-1");
    }

    @Test
    public void addToBalance_shouldSaveNewBalance_whenPeopleIsPlayer() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());
        Team expectedTeam = new Team(Color.RED, new Balance(1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String result = balanceManage.addToBalance(FAKE_UUID, 1);

        assertThat(result).isEqualTo("<success>Transaction effectuée avec succès. Nouveau solde d'émeraudes en banque : 1.");
        verify(teamsManager, times(1)).saveTeam(expectedTeam);
    }

    @Test
    public void addToBalance_shouldDoNothing_whenPeopleIsNotPlayer() {

        Team team = new Team(Color.RED, new Balance(-1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String result = balanceManage.addToBalance(FAKE_UUID, 1);

        assertThat(result).isEqualTo("<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.");
        verify(teamsManager, times(1)).getTeamOfPlayer(FAKE_UUID);
        verifyNoMoreInteractions(teamsManager);
    }

}