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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.*;

import static org.mockito.Mockito.*;

public class BankManagerTest {

    private static final UUID FAKE_UUID = UUID.randomUUID();

    private GamesInfosRepository gamesInfosRepository;
    private TimeProvider timeProvider;
    private TeamsRepository teamsRepository;
    private BalanceManage balanceManage;
    private BankManager bankManager;

    @Before
    public void setUp() {
        gamesInfosRepository = Mockito.mock(GamesInfosRepository.class);
        timeProvider = Mockito.mock(TimeProvider.class);
        balanceManage = Mockito.mock(BalanceManage.class);
        teamsRepository = Mockito.mock(TeamsRepository.class);
        bankManager = new BankManager(balanceManage, gamesInfosRepository, timeProvider, teamsRepository);
    }

    @Test
    public void depositEmeralds_shouldAddEmeraldsToBalance_whenBankIsOpenAndTeamCanDepositEmeralds() {

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
    public void depositEmeralds_shouldAdd1Emeralds_whenTeamWantDeposit20ButCanOnly10() {

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
    public void depositEmeralds_shouldDoNothing_whenBankIsNotOpen() {

        Team team = new Team(Color.RED, new Balance(0), new HashMap<>());

        when(gamesInfosRepository.getDay()).thenReturn(new Day(1));
        when(gamesInfosRepository.getEmeraldsLimitation(new Day(1))).thenReturn(new EmeraldsLimitation(128));
        when(gamesInfosRepository.getBankOpenSlots(new Day(1))).thenReturn(new BankSlots(Collections.singletonList(new Slot(10, 12))));
        when(timeProvider.getHour()).thenReturn(9);
        when(teamsRepository.getTeamOfPlayer(FAKE_UUID)).thenReturn(Optional.of(team));

        bankManager.depositEmeralds(FAKE_UUID, 1);

        verifyNoInteractions(balanceManage);
    }

    @Test
    public void depositEmeralds_shouldDoNothing_whenTeamCantDepositEmerald() {

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

}
