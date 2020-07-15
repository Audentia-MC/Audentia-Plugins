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

public class BalanceManagerTest {

    private static final UUID PLAYER_UUID = UUID.randomUUID();
    private static final Team FAKE_TEAM = new Team(Color.BLACK);

    private BalanceRepository balanceRepository;
    private BalanceManager balanceManager;

    @Before
    public void setUp() {
        TeamsManager teamsManager = Mockito.mock(TeamsManager.class);
        this.balanceRepository = Mockito.mock(BalanceRepository.class);
        this.balanceManager = new BalanceManager(teamsManager, balanceRepository);

        when(teamsManager.getTeamOfPlayer(PLAYER_UUID)).thenReturn(FAKE_TEAM);
    }

    @Test
    public void getBalanceOfPlayer_ShouldReturnSuccessMessage_WhenPlayerTeamHasBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(OptionalInt.of(0));

        String balance = this.balanceManager.getBalanceOfPlayer(PLAYER_UUID);

        assertThat(balance).isEqualTo("&#000000Balance : 0 émeraudes.");
    }

    @Test
    public void getBalanceOfPlayer_ShouldReturnErrorMessage_WhenPlayerTeamHasNotBalance() {

        when(balanceRepository.getTeamBalance(FAKE_TEAM)).thenReturn(OptionalInt.empty());

        String balance = this.balanceManager.getBalanceOfPlayer(PLAYER_UUID);

        assertThat(balance).isEqualTo("<error>Votre groupe ne possède pas de compte en banque.");
    }

}