package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.core.domain.model.bank.Slot;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
// TODO : write tests display names
@ExtendWith(MockitoExtension.class)
class BankManagerTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    @Mock
    private TimeProvider timeProvider;

    @Mock
    private TeamsRepository teamsRepository;

    @Mock
    private BalanceManage balanceManage;

    private BankManager bankManager;

    @BeforeEach
    void setUp() {
        bankManager = new BankManager(balanceManage, gamesInfosRepository, timeProvider, teamsRepository);
    }

    @Test
    void depositEmeralds_shouldAddEmeraldsToBalance_whenBankIsOpenAndTeamCanDepositEmeralds() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getEmeraldsLimitation(new Day(1))).thenReturn(new EmeraldsLimitation(128));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 1);

        verify(balanceManage, times(1)).addToBalance(FAKE_UUID, 1);
    }

    @Test
    void depositEmeralds_shouldAdd1Emeralds_whenTeamWantDeposit20ButCanOnly10() {

        Map<Day, DayTransfers> transfers = new HashMap<>();
        transfers.put(new Day(1), new DayTransfers(118));
        Team team = new Team(Color.RED, new Balance(0), transfers);

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getEmeraldsLimitation(new Day(1))).thenReturn(new EmeraldsLimitation(128));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 20);

        verify(balanceManage, times(1)).addToBalance(FAKE_UUID, 10);
    }

    @Test
    void depositEmeralds_shouldAdd10Emeralds_whenTeamWantDeposit10AndCanOnly10() {

        Map<Day, DayTransfers> transfers = new HashMap<>();
        transfers.put(new Day(1), new DayTransfers(118));
        Team team = new Team(Color.RED, new Balance(0), transfers);

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getEmeraldsLimitation(new Day(1))).thenReturn(new EmeraldsLimitation(128));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 10);

        verify(balanceManage, times(1)).addToBalance(FAKE_UUID, 10);
    }

    @Test
    void depositEmeralds_shouldDoNothing_whenHourIsBeforeBankOpenSlot() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(9);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 1);

        verifyNoInteractions(balanceManage);
    }

    @Test
    void depositEmeralds_shouldDoNothing_whenHourIsAfterBankOpenSlot() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(12);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 1);

        verifyNoInteractions(balanceManage);
    }

    @Test
    void depositEmeralds_shouldDoNothing_whenTeamCantDepositEmerald() {

        Map<Day, DayTransfers> transfers = new HashMap<>();
        transfers.put(new Day(1), new DayTransfers(128));
        Team team = new Team(Color.RED, new Balance(0), transfers);

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getEmeraldsLimitation(new Day(1))).thenReturn(new EmeraldsLimitation(128));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(10);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 1);

        verifyNoInteractions(balanceManage);
    }

    @Test
    void depositEmeralds_shouldDoNothing_whenPeopleIsNotPlayer() {


        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.empty());

        String result = bankManager.depositEmeralds(FAKE_UUID, 1);

        assertThat(result).isEqualTo("<error>Votre groupe ne peut pas accéder à la banque.");
        verifyNoInteractions(balanceManage);
    }

}
