package fr.audentia.core.domain.balance;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Color;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceManageTest {

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

        when(teamsManager.getTeamOfPlayer(any())).thenReturn(new Team(Color.RED, new Balance(0), new HashMap<>(), "Tony", 0));

        String balance = this.balanceManage.getBalanceWithMessage(any());

        assertThat(balance).isEqualTo("&#FF0000Balance : 0 émeraudes.");
    }

    @Test
    @DisplayName("getBalanceWithMessage should return a success message when the player isn't in a team of players")
    void getBalanceWithMessage_shouldReturnErrorMessage_whenPlayerTeamHasNotBalance() {

        when(teamsManager.getTeamOfPlayer(any())).thenReturn(new Team(Color.RED, new Balance(-1), new HashMap<>(), "Tony", 0));

        String balance = this.balanceManage.getBalanceWithMessage(any());

        assertThat(balance).isEqualTo("<error>Votre groupe ne possède pas de compte en banque.");
    }

    @Test
    @DisplayName("getBalance should return 10 when the player's team has 10 emeralds")
    void getBalance_shouldReturn10_whenPlayerTeamHas10Emeralds() {

        when(teamsManager.getTeamOfPlayer(any())).thenReturn(new Team(Color.RED, new Balance(10), new HashMap<>(), "Tony", 0));

        String balance = this.balanceManage.getBalance(any());

        assertThat(balance).isEqualTo("10");
    }

    @Test
    @DisplayName("getBalance should return -1 when the player isn't in a team of players")
    void getBalance_shouldReturnMinus1_whenPlayerTeamHasNotBalance() {

        when(teamsManager.getTeamOfPlayer(any())).thenReturn(new Team(Color.RED, new Balance(-1), new HashMap<>(), "Tony", 0));

        String balance = this.balanceManage.getBalance(any());

        assertThat(balance).isEqualTo("-1");
    }

    @Test
    @DisplayName("addToBalance should call teamsManager and return a success message when the player is in a team of players")
    void addToBalance_shouldSaveNewBalance_whenPeopleIsPlayer() {

        Team expectedTeam = new Team(Color.RED, new Balance(1), new HashMap<>(), "Tony", 0);
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(new Team(Color.RED, new Balance(0), new HashMap<>(), "Tony", 0));

        String result = balanceManage.addToBalance(any(), 1);

        assertThat(result).isEqualTo("<success>Dépôt effectué.");
        verify(teamsManager, times(1)).saveTeam(expectedTeam);
    }

    @Test
    @DisplayName("addToBalance should do nothing and return an error message when the player isn't in a team of players")
    void addToBalance_shouldDoNothing_whenPeopleIsNotPlayer() {

        when(teamsManager.getTeamOfPlayer(any())).thenReturn(new Team(Color.RED, new Balance(-1), new HashMap<>(), "Tony", 0));

        String result = balanceManage.addToBalance(any(), 1);

        assertThat(result).isEqualTo("<error>Votre groupe ne peut pas déposer d'émeraude dans la banque.");
        verifyNoMoreInteractions(teamsManager);
    }

}