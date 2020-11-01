package fr.audentia.core.infrastructure.scoreboard;

import fr.audentia.core.domain.model.scoreboard.Scoreboard;
import fr.audentia.core.domain.scoreboard.ScoreboardsRepository;
import fr.audentia.core.utils.FastBoard;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        lines = lines.stream().map(ChatUtils::format).collect(Collectors.toList());
        fastBoard.updateLines(lines);
    }

    @Override
    public void removeScoreboard(UUID playerUUID) {
        this.scoreboards.remove(playerUUID);
    }

}
