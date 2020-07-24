package fr.audentia.core.domain.scoreboard;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.scoreboard.Scoreboard;
import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.Team;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreboardProvideTest {

    @Mock
    private TeamsManager teamsManager;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    @Mock
    private TimeProvider timeProvider;

    private ScoreboardProvide scoreboardProvide;

    @BeforeEach
    void setUp() {
        scoreboardProvide = new ScoreboardProvide(teamsManager, rolesRepository, gamesInfosRepository, timeProvider);
    }

    @Test
    @DisplayName("buildScoreboard should return scoreboard of the team when player is a player")
    void buildScoreboard_shouldReturnScoreboardWithTeamAndBalance_whenPlayerIsAPlayer() {

        Team tony = new Team(Color.RED, new Balance(1), new HashMap<>(), "Tony");
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(new Role(false, true, 0));
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(1_000L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(950_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(920_306L);

        Scoreboard result = scoreboardProvide.buildScoreBoard(UUID.randomUUID());

        assertThat(result).isEqualTo(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("&#FF0000Team Tony")
                .addContent("1 émeraude")
                .addContent("Temps : 10:15:21:46 / 11:00:00:00")
                .withFooter("----= audentia.fr =----")
                .build());
    }

    @Test
    @DisplayName("buildScoreBoard should have an s at the end of the emeralds line when team has many emeralds")
    void buildScoreboard_shouldHaveAnSAtTheEndOfTheEmeraldsLine_whenTeamHasManyEmeralds() {

        Team tony = new Team(Color.RED, new Balance(2), new HashMap<>(), "Manu");
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(new Role(false, true, 0));
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(0L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(950_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(784_809L);

        Scoreboard result = scoreboardProvide.buildScoreBoard(UUID.randomUUID());

        assertThat(result).isEqualTo(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("&#FF0000Team Manu")
                .addContent("2 émeraudes")
                .addContent("Temps : 09:02:00:09 / 11:00:00:00")
                .withFooter("----= audentia.fr =----")
                .build());
    }

    @Test
    @DisplayName("buildScoreBoard should return empty scoreboard when player is not a player")
    void buildScoreboard_shouldReturnEmptyScoreboard_whenPlayerIsNotAPlayer() {

        Team tony = new Team(Color.RED, new Balance(2), new HashMap<>(), "Manu");
        when(teamsManager.getTeamOfPlayer(any())).thenReturn(tony);
        when(rolesRepository.getRole(any())).thenReturn(new Role(false, false, 0));
        when(gamesInfosRepository.getStartTimeInSeconds()).thenReturn(0L);
        when(gamesInfosRepository.getGameDurationInSeconds()).thenReturn(950_400L);
        when(timeProvider.getActualTimeInSeconds()).thenReturn(784_809L);

        Scoreboard result = scoreboardProvide.buildScoreBoard(UUID.randomUUID());

        assertThat(result).isEqualTo(aScoreboard()
                .withHeader("----= Audentia =----")
                .addContent("Temps : 09:02:00:09 / 11:00:00:00")
                .withFooter("----= audentia.fr =----")
                .build());
    }

}
