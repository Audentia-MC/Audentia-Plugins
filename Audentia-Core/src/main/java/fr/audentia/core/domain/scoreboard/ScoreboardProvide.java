package fr.audentia.core.domain.scoreboard;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.scoreboard.Scoreboard;
import fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder;
import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.UUID;

import static fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder.aScoreboard;

public class ScoreboardProvide {

    public static final String DURATION_FORMAT = "dd:HH:mm:ss";

    private final TeamsManager teamsManager;
    private final RolesRepository rolesRepository;
    private final GamesInfosRepository gamesInfosRepository;
    private final TimeProvider timeProvider;
    private final EventsRepository eventsRepository;

    public ScoreboardProvide(TeamsManager teamsManager, RolesRepository rolesRepository, GamesInfosRepository gamesInfosRepository, TimeProvider timeProvider, EventsRepository eventsRepository) {
        this.teamsManager = teamsManager;
        this.rolesRepository = rolesRepository;
        this.gamesInfosRepository = gamesInfosRepository;
        this.timeProvider = timeProvider;
        this.eventsRepository = eventsRepository;
    }

    public Scoreboard buildScoreBoard(UUID playerUUID) {

        Team team = teamsManager.getTeamOfPlayer(playerUUID);
        Role role = rolesRepository.getRole(playerUUID);

        ScoreboardBuilder builder = aScoreboard()
                .withHeader("----= Audentia =----");

        if (role.player) {

            String balance = team.balance.toString();
            builder.addContent("&" + ColorsUtils.fromColorToHexadecimal(team.color) + "Team " + team.name)
                    .addContent(balance + " émeraude" + ("0 1".contains(balance) ? "" : "s"));
        }

        long actualTime = timeProvider.getActualTimeInSeconds();
        long gameDuration = gamesInfosRepository.getGameDurationInSeconds();
        long nextEventTime = eventsRepository.getNextEvent().time;

        String duration = getDuration(actualTime);
        return builder.addContent("Temps : " + duration + " / " + getDuration(gameDuration))
                .addContent("Prochain event : " + getDuration(nextEventTime))
                .withFooter("----= audentia.fr =----")
                .build();
    }

    private String getDuration(long time) {

        long startTimeInSeconds = gamesInfosRepository.getStartTimeInSeconds();

        return DurationFormatUtils.formatDuration((time - startTimeInSeconds) * 1_000L, DURATION_FORMAT, true);
    }

}
