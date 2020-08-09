package fr.audentia.core.main;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.bank.BankSlotsProvide;
import fr.audentia.core.domain.bank.BankSlotsRepository;
import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.home.*;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import fr.audentia.core.domain.scoreboard.ScoreboardManage;
import fr.audentia.core.domain.scoreboard.ScoreboardsRepository;
import fr.audentia.core.domain.staff.StaffInventoryOpen;
import fr.audentia.core.domain.staff.StaffInventoryOpener;
import fr.audentia.core.domain.staff.WorldPlayerFinder;
import fr.audentia.core.domain.staff.ban.BanAction;
import fr.audentia.core.domain.staff.ban.BanRepository;
import fr.audentia.core.domain.staff.ban.PlayerBanner;
import fr.audentia.core.domain.staff.inventory.LookInventoryAction;
import fr.audentia.core.domain.staff.inventory.PlayerInventoryOpener;
import fr.audentia.core.domain.staff.kick.KickAction;
import fr.audentia.core.domain.staff.kick.PlayerKicker;
import fr.audentia.core.domain.staff.teleport.PlayerTeleporter;
import fr.audentia.core.domain.staff.teleport.TeleportAction;
import fr.audentia.core.infrastructure.bank.DefaultTimeProvider;
import fr.audentia.core.infrastructure.bank.MariaDbBankSlotsRepository;
import fr.audentia.core.infrastructure.game.MariaDbGamesInfosRepository;
import fr.audentia.core.infrastructure.home.MariaDbHomeRepository;
import fr.audentia.core.infrastructure.home.SpigotPlayerTeleport;
import fr.audentia.core.infrastructure.home.SpigotWorldNameFinder;
import fr.audentia.core.infrastructure.scoreboard.MariaDbEventsRepository;
import fr.audentia.core.infrastructure.scoreboards.FastBoardScoreboardsRepository;
import fr.audentia.core.infrastructure.staff.DefaultStaffInventoryOpener;
import fr.audentia.core.infrastructure.staff.SpigotWorldPlayerFinder;
import fr.audentia.core.infrastructure.staff.ban.MariaDbBanRepository;
import fr.audentia.core.infrastructure.staff.ban.SpigotPlayerBanner;
import fr.audentia.core.infrastructure.staff.inventory.SpigotPlayerInventoryOpener;
import fr.audentia.core.infrastructure.staff.kick.SpigotPlayerKicker;
import fr.audentia.core.infrastructure.staff.teleport.SpigotPlayerTeleporter;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;
import fr.audentia.players.utils.DataBaseConfigurationLoader;

public class AudentiaCoreManagersProvider {

    public final BalanceManage balanceManage;
    public final HomeManage homeManage;
    public final SetHomeManage setHomeManage;
    public final HomesProvide homesProvide;
    public final BankSlotsProvide bankSlotsProvide;
    public final StaffInventoryOpen staffInventoryOpen;
    public final BanAction banAction;
    public final KickAction kickAction;
    public final TeleportAction teleportAction;
    public final LookInventoryAction lookInventoryAction;
    public final ScoreboardManage scoreboardManage;

    public AudentiaCoreManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider, String path) {

        DatabaseConnection databaseConnection = DataBaseConfigurationLoader.loadConnection(path);

        HomeRepository homeRepository = new MariaDbHomeRepository(databaseConnection);
        PlayerTeleport playerTeleporter = new SpigotPlayerTeleport();
        WorldNameFinder worldNameFinder = new SpigotWorldNameFinder();
        GamesInfosRepository gamesInfosRepository = new MariaDbGamesInfosRepository(databaseConnection);
        BankSlotsRepository bankSlotsRepository = new MariaDbBankSlotsRepository(databaseConnection);
        PlayerBanner playerBanner = new SpigotPlayerBanner();
        BanRepository banRepository = new MariaDbBanRepository(databaseConnection);
        PlayerKicker playerKicker = new SpigotPlayerKicker();
        PlayerTeleporter playerTeleporterToOther = new SpigotPlayerTeleporter();
        WorldPlayerFinder worldPlayerFinder = new SpigotWorldPlayerFinder();
        PlayerInventoryOpener playerInventoryOpener = new SpigotPlayerInventoryOpener();
        TimeProvider timeProvider = new DefaultTimeProvider();
        EventsRepository eventsRepository = new MariaDbEventsRepository(gamesInfosRepository, databaseConnection);
        ScoreboardsRepository scoreboardsRepository = new FastBoardScoreboardsRepository();

        this.banAction = new BanAction(playerBanner, banRepository, audentiaPlayersManagersProvider.ROLES_REPOSITORY);
        this.kickAction = new KickAction(playerKicker, audentiaPlayersManagersProvider.ROLES_REPOSITORY);
        this.teleportAction = new TeleportAction(playerTeleporterToOther, audentiaPlayersManagersProvider.ROLES_REPOSITORY, worldPlayerFinder);
        this.lookInventoryAction = new LookInventoryAction(playerInventoryOpener, audentiaPlayersManagersProvider.ROLES_REPOSITORY, worldPlayerFinder);

        StaffInventoryOpener staffInventoryOpener= new DefaultStaffInventoryOpener(banAction, kickAction, teleportAction, lookInventoryAction);

        this.balanceManage = new BalanceManage(audentiaPlayersManagersProvider.TEAMS_MANAGER);
        this.homeManage = new HomeManage(homeRepository, playerTeleporter);
        this.setHomeManage = new SetHomeManage(homeRepository, audentiaPlayersManagersProvider.ROLES_REPOSITORY, worldNameFinder);
        this.homesProvide = new HomesProvide(homeRepository);
        this.bankSlotsProvide = new BankSlotsProvide(gamesInfosRepository, bankSlotsRepository);
        this.staffInventoryOpen = new StaffInventoryOpen(audentiaPlayersManagersProvider.ROLES_REPOSITORY, staffInventoryOpener, worldPlayerFinder);
        this.scoreboardManage = new ScoreboardManage(audentiaPlayersManagersProvider.TEAMS_MANAGER, audentiaPlayersManagersProvider.ROLES_REPOSITORY, gamesInfosRepository, timeProvider, eventsRepository, scoreboardsRepository);
    }

}
