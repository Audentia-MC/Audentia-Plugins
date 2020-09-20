package fr.audentia.core.domain.scoreboard;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.scoreboard.Event;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

import static fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder.aScoreboard;
import static fr.audentia.players.domain.model.roles.RoleBuilder.aRole;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreboardManageTest {

    @Mock
    private TeamsManager teamsManager;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    @Mock
    private TimeProvider timeProvider;

    @Mock
    private EventsRepository eventsRepository;

    @Mock
    private ScoreboardsRepository scoreboardsRepository;

    private ScoreboardManage scoreboardManage;

    @BeforeEach
    void setUp() {
        scoreboardManage = new ScoreboardManage(teamsManager, rolesRepository, gamesInfosRepository, timeProvider, eventsRepository, scoreboardsRepository);
    }

    @Test
    @DisplayName("updateScoreboard should return scoreboard of the team when player is a player")
    void updateScoreboard_shouldReturnScoreboardWithTeamAndBalance_whenPlayerIsAPlayer() {

        Team tony = new Team(Color.RED, new Balance(1), new HashMap<>(), "Tony", 0);
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(aRole()
                .withName("Admin")
                .withColor(Color.BLACK)
                .withNumber(0)
                .withHomeCount(1)
                .isPlayer(true)
                .isStaff(false)
                .build());
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(1_000L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(951_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(920_306L);
        when(eventsRepository.getNextEvent()).thenReturn(new Event(40_182L));

        scoreboardManage.updateScoreboard(UUID.randomUUID());

        verify(scoreboardsRepository, times(1)).updateScoreboard(any(), eq(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("&#FF0000Team Tony")
                .addContent("1 émeraude")
                .addContent("Temps : 10:15:21:46 / 11:00:00:00")
                .addContent("Prochain event : 00:10:53:02")
                .withFooter("----= audentia.fr =----")
                .build()));
    }

    @Test
    @DisplayName("updateScoreboard should have an s at the end of the emeralds line when team has many emeralds")
    void updateScoreboard_shouldHaveAnSAtTheEndOfTheEmeraldsLine_whenTeamHasManyEmeralds() {

        Team tony = new Team(Color.RED, new Balance(2), new HashMap<>(), "Manu", 0);
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(aRole()
                .withName("Admin")
                .withColor(Color.BLACK)
                .withNumber(0)
                .withHomeCount(1)
                .isPlayer(true)
                .isStaff(false)
                .build());
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(0L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(950_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(1L);
        when(eventsRepository.getNextEvent()).thenReturn(new Event(39_182L));

        scoreboardManage.updateScoreboard(UUID.randomUUID());

        verify(scoreboardsRepository, times(1)).updateScoreboard(any(), eq(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("&#FF0000Team Manu")
                .addContent("2 émeraudes")
                .addContent("Temps : 00:00:00:01 / 11:00:00:00")
                .addContent("Prochain event : 00:10:53:02")
                .withFooter("----= audentia.fr =----")
                .build()));
    }

    @Test
    @DisplayName("updateScoreboard shouldn't have an event line when there is 0 futur event")
    void updateScoreboard_shouldNotHaveEventLine_whenThereIsFuturEvent() {

        Team tony = new Team(Color.RED, new Balance(2), new HashMap<>(), "Manu", 0);
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(aRole()
                .withName("Admin")
                .withColor(Color.BLACK)
                .withNumber(0)
                .withHomeCount(1)
                .isPlayer(true)
                .isStaff(false)
                .build());
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(0L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(950_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(1L);
        when(eventsRepository.getNextEvent()).thenReturn(new Event(0));

        scoreboardManage.updateScoreboard(UUID.randomUUID());

        verify(scoreboardsRepository, times(1)).updateScoreboard(any(), eq(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("&#FF0000Team Manu")
                .addContent("2 émeraudes")
                .addContent("Temps : 00:00:00:01 / 11:00:00:00")
                .withFooter("----= audentia.fr =----")
                .build()));
    }

    @Test
    @DisplayName("updateScoreboard should return empty scoreboard when player is not a player")
    void updateScoreboard_shouldReturnEmptyScoreboard_whenPlayerIsNotAPlayer() {

        Team tony = new Team(Color.RED, new Balance(2), new HashMap<>(), "Manu", 0);
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(aRole()
                .withName("Admin")
                .withColor(Color.BLACK)
                .withNumber(0)
                .withHomeCount(1)
                .isPlayer(false)
                .isStaff(false)
                .build());
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(0L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(950_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(784_809L);
        when(eventsRepository.getNextEvent()).thenReturn(new Event(6_057L));

        scoreboardManage.updateScoreboard(UUID.randomUUID());

        verify(scoreboardsRepository, times(1)).updateScoreboard(any(), eq(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("Temps : 09:02:00:09 / 11:00:00:00")
                .addContent("Prochain event : 00:01:40:57")
                .withFooter("----= audentia.fr =----")
                .build()));
    }

}
