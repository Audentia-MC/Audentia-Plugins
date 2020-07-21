package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Color;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceManageTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    @Mock
    private TeamsManager teamsManager;

    private BalanceManage balanceManage;

    @BeforeEach
    void setUp() {
        this.balanceManage = new BalanceManage(teamsManager);
    }

    @Test
    @DisplayName("getBalanceWithMessage should return a success message when the player is in a team of players")
    void getBalanceWithMessage_shouldReturnSuccessMessage_whenPlayerTeamHasBalance() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalanceWithMessage(FAKE_UUID);

        assertThat(balance).isEqualTo("&#FF0000Balance : 0 émeraudes.");
    }

    @Test
    @DisplayName("getBalanceWithMessage should return a success message when the player isn't in a team of players")
    void getBalanceWithMessage_shouldReturnErrorMessage_whenPlayerTeamHasNotBalance() {

        Team team = new Team(Color.RED, new Balance(-1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalanceWithMessage(FAKE_UUID);

        assertThat(balance).isEqualTo("<error>Votre groupe ne possède pas de compte en banque.");
    }

    @Test
    @DisplayName("getBalance should return 10 when the player's team has 10 emeralds")
    void getBalance_shouldReturn10_whenPlayerTeamHas10Emeralds() {

        Team team = new Team(Color.RED, new Balance(10), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalance(FAKE_UUID);

        assertThat(balance).isEqualTo("10");
    }

    @Test
    @DisplayName("getBalance should return -1 when the player isn't in a team of players")
    void getBalance_shouldReturnMinus1_whenPlayerTeamHasNotBalance() {

        Team team = new Team(Color.RED, new Balance(-1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String balance = this.balanceManage.getBalance(FAKE_UUID);

        assertThat(balance).isEqualTo("-1");
    }

    @Test
    @DisplayName("addToBalance should call teamsManager and return a success message when the player is in a team of players")
    void addToBalance_shouldSaveNewBalance_whenPeopleIsPlayer() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());
        Team expectedTeam = new Team(Color.RED, new Balance(1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String result = balanceManage.addToBalance(FAKE_UUID, 1);

        assertThat(result).isEqualTo("<success>Transaction effectuée avec succès. Nouveau solde d'émeraudes en banque : 1.");
        verify(teamsManager, times(1)).saveTeam(expectedTeam);
    }

    @Test
    @DisplayName("addToBalance should do nothing and return an error message when the player isn't in a team of players")
    void addToBalance_shouldDoNothing_whenPeopleIsNotPlayer() {

        Team team = new Team(Color.RED, new Balance(-1), new HashMap<>());
        when(teamsManager.getTeamOfPlayer(FAKE_UUID)).thenReturn(team);

        String result = balanceManage.addToBalance(FAKE_UUID, 1);

        assertThat(result).isEqualTo("<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.");
        verify(teamsManager, times(1)).getTeamOfPlayer(FAKE_UUID);
        verifyNoMoreInteractions(teamsManager);
    }

}