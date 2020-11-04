package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.core.domain.model.bank.Slot;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankManageTest {

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    @Mock
    private TimeProvider timeProvider;

    @Mock
    private TeamsManager teamsManager;

    @Mock
    private BankSlotsRepository bankSlotsRepository;

    @Mock
    private BalanceManage balanceManage;

    private BankManage bankManage;

    @BeforeEach
    void setUp() {
        bankManage = new BankManage(balanceManage, gamesInfosRepository, bankSlotsRepository, timeProvider, teamsManager);
    }

    @Test
    @DisplayName("depositEmeralds should call balanceManage when bank is open and team hasn't reached daily limitation")
    void depositEmeralds_shouldAddEmeraldsToBalance_whenBankIsOpenAndTeamCanDepositEmeralds() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>(), new HashMap<>(), "Tony", 0);
        when(balanceManage.addToBalance(any(), anyInt())).thenReturn("<success>Dépôt effectué.");
        when(gamesInfosRepository.getEmeraldsLimitation(any())).thenReturn(new EmeraldsLimitation(128));
        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsManager.getTeam(any())).thenReturn(team);

        String result = bankManage.depositEmeralds(UUID.randomUUID(), 1);

        verify(balanceManage, times(1)).addToBalance(any(), eq(1));
        assertThat(result).isEqualTo("<success>Dépôt effectué.");
    }

    @Test
    @DisplayName("depositEmeralds should call balanceManage to add only 10 emeralds when player want to add 20 but team can only deposit 10")
    void depositEmeralds_shouldAdd1Emeralds_whenTeamWantDeposit20ButCanOnly10() {

        Map<Day, DayTransfers> transfers = new HashMap<>();
        transfers.put(new Day(1), new DayTransfers(118));
        Team team = new Team(Color.RED, new Balance(0), transfers, new HashMap<>(), "Tony", 0);

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(balanceManage.addToBalance(any(), anyInt())).thenReturn("<success>Dépôt effectué.");
        when(gamesInfosRepository.getEmeraldsLimitation(any())).thenReturn(new EmeraldsLimitation(128));
        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsManager.getTeam(any())).thenReturn(team);

        String result = bankManage.depositEmeralds(UUID.randomUUID(), 20);

        verify(balanceManage, times(1)).addToBalance(any(), eq(10));
        assertThat(result).isEqualTo("<success>Dépôt effectué.");
    }

    @Test
    @DisplayName("depositEmeralds shouldn't call balanceManage when bank time is before a bank open slot")
    void depositEmeralds_shouldDoNothing_whenHourIsBeforeBankOpenSlot() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>(), new HashMap<>(), "Tony", 0);

        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(9);
        when(teamsManager.getTeam(any())).thenReturn(team);

        String result = bankManage.depositEmeralds(UUID.randomUUID(), 1);

        verifyNoInteractions(balanceManage);
        assertThat(result).isEqualTo("<error>La banque est fermée.");
    }

    @Test
    @DisplayName("depositEmeralds shouldn't call balanceManage when bank time is after a bank open slot")
    void depositEmeralds_shouldDoNothing_whenHourIsAfterBankOpenSlot() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>(), new HashMap<>(), "Tony", 0);

        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(12);
        when(teamsManager.getTeam(any())).thenReturn(team);

        String result = bankManage.depositEmeralds(UUID.randomUUID(), 1);

        verifyNoInteractions(balanceManage);
        assertThat(result).isEqualTo("<error>La banque est fermée.");
    }

    @Test
    @DisplayName("depositEmeralds shouldn't call balanceManage when team has reached daily limitation")
    void depositEmeralds_shouldDoNothing_whenTeamCantDepositEmerald() {

        Map<Day, DayTransfers> transfers = new HashMap<>();
        transfers.put(new Day(1), new DayTransfers(128));
        Team team = new Team(Color.RED, new Balance(0), transfers, new HashMap<>(), "Tony", 0);

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getEmeraldsLimitation(any())).thenReturn(new EmeraldsLimitation(128));
        when(bankSlotsRepository.getBankOpenSlots(any())).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsManager.getTeam(any())).thenReturn(team);

        String result = bankManage.depositEmeralds(UUID.randomUUID(), 1);

        verifyNoInteractions(balanceManage);
        assertThat(result).isEqualTo("<error>Vous avez déjà déposé le maximum d'émeraudes possible pour aujourd'hui.");
    }

    @Test
    @DisplayName("depositEmeralds shouldn't call balanceManage when player is not a player")
    void depositEmeralds_shouldDoNothing_whenPeopleIsNotPlayer() {

        Team team = new Team(Color.BLACK, new Balance(0), new HashMap<>(), new HashMap<>(), "Tony", 0);

        when(teamsManager.getTeam(any())).thenReturn(team);

        String result = bankManage.depositEmeralds(UUID.randomUUID(), 1);

        assertThat(result).isEqualTo("<error>Votre groupe ne peut pas accéder à la banque.");
        verifyNoInteractions(balanceManage);
    }

}
