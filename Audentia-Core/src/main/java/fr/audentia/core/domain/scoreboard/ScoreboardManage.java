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

import java.util.UUID;

import static fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder.aScoreboard;

public class ScoreboardManage {

    public static final String DURATION_FORMAT = "ddj HH:mm";

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

        builder.addContent("&" + ColorsUtils.fromColorToHexadecimal(team.color) + team.name);

        if (role.player && !role.staff) {
            String balance = team.balance.toString();
            builder.addContent(balance + " émeraude" + ("0 1".contains(balance) ? "" : "s"));
        }

        addTimedInfos(builder);

        scoreboardsRepository.updateScoreboard(playerUUID, builder.withFooter("---= audentia.fr =---").build());
    }

    private void addTimedInfos(ScoreboardBuilder builder) {
        long startTimeInSeconds = gamesInfosRepository.getStartTimeInSeconds();

        if (startTimeInSeconds == -1) {
            return;
        }

        long actualTime = timeProvider.getActualTimeInSeconds();
        long actualTimeInGame = actualTime - startTimeInSeconds;
        long gameDuration = gamesInfosRepository.getGameDurationInSeconds();
        builder.addContent("Temps : " + getDuration(actualTimeInGame));
        builder.addContent("Total : " + getDuration(gameDuration));

        long nextEventTime = eventsRepository.getNextEvent().time;

        if (nextEventTime != -1) {
            builder.addContent("Event dans : " + getDuration(nextEventTime - actualTime));
        }
    }

    private String getDuration(long time) {
        return DurationFormatUtils.formatDuration(time * 1_000L, DURATION_FORMAT, true);
    }

    public void removeScoreBoard(UUID playerUUID) {
        scoreboardsRepository.removeScoreboard(playerUUID);
    }

}
