package fr.audentia.players.application.tasks;

import fr.audentia.players.domain.model.tablist.PlayerTabList;
import fr.audentia.players.domain.tablist.TabListProvider;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TabListTask extends BukkitRunnable {

    private final TabListProvider tabListProvider;

    public TabListTask(TabListProvider tabListProvider) {
        this.tabListProvider = tabListProvider;
    }

    @Override
    public void run() {

        Bukkit.getOnlinePlayers().forEach(this::updateTabList);

    }

    private void updateTabList(Player player) {

        PlayerTabList playerTabList = tabListProvider.getTabList(player.getUniqueId());

        player.setPlayerListHeader(ChatUtils.format(playerTabList.header));
        player.setPlayerListFooter(ChatUtils.format(playerTabList.footer));
        player.setPlayerListName(ChatUtils.format(playerTabList.prefix + player.getName()));

        Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team team = mainScoreboard.getTeam(playerTabList.team);

        if (team == null) {
            team = mainScoreboard.registerNewTeam(playerTabList.team);
        }

        team.addEntry(player.getName());

    }

}
