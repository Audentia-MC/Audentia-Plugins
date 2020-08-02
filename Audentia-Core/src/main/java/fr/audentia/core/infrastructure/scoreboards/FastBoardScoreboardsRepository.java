package fr.audentia.core.infrastructure.scoreboards;

import fr.audentia.core.domain.model.scoreboard.Scoreboard;
import fr.audentia.core.domain.scoreboard.ScoreboardsRepository;
import fr.audentia.core.utils.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FastBoardScoreboardsRepository implements ScoreboardsRepository {

    private final Map<UUID, FastBoard> scoreboards = new HashMap<>();

    @Override
    public void updateScoreboard(UUID playerUUID, Scoreboard scoreboard) {

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            return;
        }

        if (!scoreboards.containsKey(playerUUID)) {
            scoreboards.put(playerUUID, new FastBoard(player));
        }

        FastBoard fastBoard = scoreboards.get(playerUUID);

        fastBoard.updateTitle(scoreboard.header);

        List<String> lines = scoreboard.content;
        lines.add(scoreboard.footer);
        fastBoard.updateLines(lines);

    }

    @Override
    public void removeScoreboard(UUID playerUUID) {
        this.scoreboards.remove(playerUUID);
    }

}
