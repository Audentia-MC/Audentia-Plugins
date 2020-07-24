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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

import static fr.audentia.core.domain.model.scoreboard.ScoreboardBuilder.aScoreboard;

public class ScoreboardProvide {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:HH:mm:ss");

    private final TeamsManager teamsManager;
    private final RolesRepository rolesRepository;
    private final GamesInfosRepository gamesInfosRepository;
    private final TimeProvider timeProvider;

    public ScoreboardProvide(TeamsManager teamsManager, RolesRepository rolesRepository, GamesInfosRepository gamesInfosRepository, TimeProvider timeProvider) {
        this.teamsManager = teamsManager;
        this.rolesRepository = rolesRepository;
        this.gamesInfosRepository = gamesInfosRepository;
        this.timeProvider = timeProvider;
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

        String gameDuration = Instant.ofEpochSecond(gamesInfosRepository.getGameDurationInSeconds())
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minus(Period.ofDays(1))
                .format(formatter);

        long elapsedTimeInSeconds = timeProvider.getActualTimeInSeconds() - gamesInfosRepository.getStartTimeInSeconds();
        String elapsedTime = Instant.ofEpochSecond(elapsedTimeInSeconds)
                .atOffset(ZoneOffset.UTC)
                .toLocalDateTime()
                .minus(Period.ofDays(1))
                .format(formatter);

        builder.addContent("Temps : " + elapsedTime + " / " + gameDuration);

        return builder
                .withFooter("----= audentia.fr =----")
                .build();
    }

}
