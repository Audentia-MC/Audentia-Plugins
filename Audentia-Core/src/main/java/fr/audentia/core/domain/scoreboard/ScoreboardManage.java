package fr.audentia.core.domain.scoreboard;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import static fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder.aScoreboard;

public class ScoreboardManage {

    public static final String DURATION_FORMAT = "ddj HH:mm:ss";

    private final TeamsManager teamsManager;
    private final RolesRepository rolesRepository;
    private final GamesInfosRepository gamesInfosRepository;
    private final TimeProvider timeProvider;
    private final EventsRepository eventsRepository;
    private final ScoreboardsRepository scoreboardsRepository;

    public ScoreboardManage(TeamsManager teamsManager,
                            RolesRepository rolesRepository,
                            GamesInfosRepository gamesInfosRepository,
                            TimeProvider timeProvider,
                            EventsRepository eventsRepository,
                            ScoreboardsRepository scoreboardsRepository) {
        this.teamsManager = teamsManager;
        this.rolesRepository = rolesRepository;
        this.gamesInfosRepository = gamesInfosRepository;
        this.timeProvider = timeProvider;
        this.eventsRepository = eventsRepository;
        this.scoreboardsRepository = scoreboardsRepository;
    }

    public void updateScoreboard(UUID playerUUID) {

        Team team = teamsManager.getTeam(playerUUID);
        Role role = rolesRepository.getRole(playerUUID);

        ScoreboardBuilder builder = aScoreboard().withHeader("---=  Audentia  =---");

        builder.addContent("&" + ColorsUtils.fromColorToHexadecimal(team.color) + ((team.name.length() > 15 ? team.name.substring(0, 12) + "..." : team.name)));

        if (role.isPlayer() && !role.isStaff()) {
            String balance = team.balance.toString();
            builder.addContent(balance + " émeraude" + ("0 1".contains(balance) ? "" : "s"));
        }

        addTimedInfos(builder);

        scoreboardsRepository.updateScoreboard(playerUUID, builder.withFooter("---= audentia.fr =---").build());
    }

    private void addTimedInfos(ScoreboardBuilder builder) {
        LocalDateTime start = gamesInfosRepository.getStart();

        if (start.isBefore(LocalDateTime.now())) {
            return;
        }

        LocalDateTime actualTime = timeProvider.getActualTime();
        Duration actualTimeInGame = Duration.between(start, actualTime);

        LocalDateTime end = gamesInfosRepository.getEnd();
        Duration gameDuration = Duration.between(start, end);

        builder.addContent("Temps : " + getDuration(actualTimeInGame.toMillis()));
        builder.addContent("Total : " + getDuration(gameDuration.toMillis()));

        ZonedDateTime nextEventTime = eventsRepository.getNextEvent();

        if (nextEventTime != null) {
            Duration nextEvent = Duration.between(actualTime, nextEventTime);
            builder.addContent("Event dans : " + getDuration(nextEvent.toMillis()));
        }
    }

    private String getDuration(long time) {
        return DurationFormatUtils.formatDuration(time, DURATION_FORMAT, true);
    }

    public void removeScoreBoard(UUID playerUUID) {
        scoreboardsRepository.removeScoreboard(playerUUID);
    }

}
