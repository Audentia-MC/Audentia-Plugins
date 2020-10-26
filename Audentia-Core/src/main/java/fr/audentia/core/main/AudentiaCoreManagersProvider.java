package fr.audentia.core.main;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.bank.*;
import fr.audentia.core.domain.border.BorderCreate;
import fr.audentia.core.domain.border.BorderInfosRepository;
import fr.audentia.core.domain.border.BorderSpawner;
import fr.audentia.core.domain.damage.ColiseumLocationRepository;
import fr.audentia.core.domain.damage.PlayerDamage;
import fr.audentia.core.domain.event.EventProvider;
import fr.audentia.core.domain.game.GameStateManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.home.*;
import fr.audentia.core.domain.npc.*;
import fr.audentia.core.domain.players.MoveManage;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import fr.audentia.core.domain.scoreboard.ScoreboardManage;
import fr.audentia.core.domain.scoreboard.ScoreboardsRepository;
import fr.audentia.core.domain.shop.ShopInventoryOpen;
import fr.audentia.core.domain.shop.ShopInventoryOpener;
import fr.audentia.core.domain.shop.ShopItemBuyAction;
import fr.audentia.core.domain.shop.ShopRepository;
import fr.audentia.core.domain.staff.StaffInventoryOpen;
import fr.audentia.core.domain.staff.StaffInventoryOpener;
import fr.audentia.core.domain.staff.WorldPlayerFinder;
import fr.audentia.core.domain.staff.ban.BanAction;
import fr.audentia.core.domain.staff.ban.BanRepository;
import fr.audentia.core.domain.staff.ban.PlayerBanner;
import fr.audentia.core.domain.staff.grade.GradeChangeAction;
import fr.audentia.core.domain.staff.grade.GradeInventoryAction;
import fr.audentia.core.domain.staff.grade.GradeInventoryOpener;
import fr.audentia.core.domain.staff.inventory.LookInventoryAction;
import fr.audentia.core.domain.staff.inventory.PlayerInventoryOpener;
import fr.audentia.core.domain.staff.kick.KickAction;
import fr.audentia.core.domain.staff.kick.PlayerKicker;
import fr.audentia.core.domain.staff.teleport.PlayerTeleporter;
import fr.audentia.core.domain.staff.teleport.TeleportAction;
import fr.audentia.core.infrastructure.bank.*;
import fr.audentia.core.infrastructure.border.SpigotBorderSpawner;
import fr.audentia.core.infrastructure.border.TOMLBorderInfosRepository;
import fr.audentia.core.infrastructure.damage.TOMLColiseumLocationRepository;
import fr.audentia.core.infrastructure.game.MariaDbGamesInfosRepository;
import fr.audentia.core.infrastructure.home.MariaDbHomeRepository;
import fr.audentia.core.infrastructure.home.SpigotPlayerTeleport;
import fr.audentia.core.infrastructure.home.SpigotWorldNameFinder;
import fr.audentia.core.infrastructure.npc.SpigotNpcSpawner;
import fr.audentia.core.infrastructure.npc.SpigotWorldNpcFinder;
import fr.audentia.core.infrastructure.npc.TOMLNetherNpcRepository;
import fr.audentia.core.infrastructure.npc.TOMLNpcRepository;
import fr.audentia.core.infrastructure.scoreboard.FastBoardScoreboardsRepository;
import fr.audentia.core.infrastructure.scoreboard.MariaDbEventsRepository;
import fr.audentia.core.infrastructure.shop.SpigotShopInventoryOpener;
import fr.audentia.core.infrastructure.shop.TOMLNetherShopRepository;
import fr.audentia.core.infrastructure.shop.TOMLShopRepository;
import fr.audentia.core.infrastructure.staff.DefaultStaffInventoryOpener;
import fr.audentia.core.infrastructure.staff.SpigotWorldPlayerFinder;
import fr.audentia.core.infrastructure.staff.ban.MariaDbBanRepository;
import fr.audentia.core.infrastructure.staff.ban.SpigotPlayerBanner;
import fr.audentia.core.infrastructure.staff.grade.DefaultGradeInventoryOpener;
import fr.audentia.core.infrastructure.staff.inventory.SpigotPlayerInventoryOpener;
import fr.audentia.core.infrastructure.staff.kick.SpigotPlayerKicker;
import fr.audentia.core.infrastructure.staff.teleport.SpigotPlayerTeleporter;
import fr.audentia.players.domain.teams.RolesRepository;
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
    public final EventProvider eventProvider;
    public final GameStateManage gameStateManage;
    public final NpcInteract npcInteract;
    public final BankInventoryInteract bankInventoryInteract;
    public final BankManage bankManage;
    public final NpcSpawn npcSpawn;
    public final BorderCreate borderCreate;
    public final RolesRepository rolesRepository;
    public final GradeInventoryAction gradeInventoryAction;
    public final GradeChangeAction gradeChangeAction;
    public final PlayerDamage playerDamage;
    public final NetherNcpSpawn netherNpcSpawn;
    public final MoveManage moveManage;

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
        BankNpcProvider bankNpcProvider = new TOMLBankNpcProvider(path);
        InventoryUtilities inventoryUtilities = new SpigotInventoryUtilities();
        NpcSpawner npcSpawner = new SpigotNpcSpawner();
        NpcRepository npcRepository = new TOMLNpcRepository(path);
        WorldNpcFinder worldNpcFinder = new SpigotWorldNpcFinder();
        BorderInfosRepository borderInfosRepository = new TOMLBorderInfosRepository(path);
        BorderSpawner borderSpawner = new SpigotBorderSpawner();
        ShopRepository shopRepository = new TOMLShopRepository(path);
        ShopRepository netherShopRepository = new TOMLNetherShopRepository(path);
        ColiseumLocationRepository coliseumLocationRepository = new TOMLColiseumLocationRepository(path);
        NetherNpcRepository netherNpcRepository = new TOMLNetherNpcRepository(path);

        this.banAction = new BanAction(playerBanner, banRepository, audentiaPlayersManagersProvider.rolesRepository);
        this.kickAction = new KickAction(playerKicker, audentiaPlayersManagersProvider.rolesRepository);
        this.teleportAction = new TeleportAction(playerTeleporterToOther, audentiaPlayersManagersProvider.rolesRepository, worldPlayerFinder);
        this.lookInventoryAction = new LookInventoryAction(playerInventoryOpener, audentiaPlayersManagersProvider.rolesRepository, worldPlayerFinder);
        this.balanceManage = new BalanceManage(audentiaPlayersManagersProvider.teamsManager);
        this.bankManage = new BankManage(balanceManage, gamesInfosRepository, bankSlotsRepository, timeProvider, audentiaPlayersManagersProvider.teamsManager);
        this.bankInventoryInteract = new BankInventoryInteract(inventoryUtilities, bankManage);
        this.gradeChangeAction = new GradeChangeAction(audentiaPlayersManagersProvider.rolesRepository);
        this.playerDamage = new PlayerDamage(audentiaPlayersManagersProvider.teamsManager, audentiaPlayersManagersProvider.rolesRepository, balanceManage, coliseumLocationRepository);
        this.netherNpcSpawn = new NetherNcpSpawn(npcSpawner, netherNpcRepository, worldNpcFinder);
        this.gameStateManage = new GameStateManage(gamesInfosRepository);
        this.moveManage = new MoveManage(audentiaPlayersManagersProvider.rolesRepository, gameStateManage, gamesInfosRepository);
        ShopItemBuyAction shopItemBuyAction = new ShopItemBuyAction(inventoryUtilities);

        ShopInventoryOpener shopInventoryOpener = new SpigotShopInventoryOpener(shopItemBuyAction, balanceManage);
        BankInventoryOpener bankInventoryOpener = new DefaultBankInventoryOpener(bankInventoryInteract);
        GradeInventoryOpener gradeInventoryOpener = new DefaultGradeInventoryOpener(gradeChangeAction, audentiaPlayersManagersProvider.rolesRepository);

        BankInventoryOpen bankInventoryOpen = new BankInventoryOpen(audentiaPlayersManagersProvider.teamsManager, bankInventoryOpener, audentiaPlayersManagersProvider.rolesRepository);
        ShopInventoryOpen shopInventoryOpen = new ShopInventoryOpen(shopRepository, shopInventoryOpener);
        ShopInventoryOpen netherShopInventoryOpen = new ShopInventoryOpen(netherShopRepository, shopInventoryOpener);
        this.gradeInventoryAction = new GradeInventoryAction(audentiaPlayersManagersProvider.rolesRepository, gradeInventoryOpener);

        StaffInventoryOpener staffInventoryOpener = new DefaultStaffInventoryOpener(banAction, kickAction, teleportAction, lookInventoryAction, gradeInventoryAction);

        this.homeManage = new HomeManage(homeRepository, playerTeleporter);
        this.setHomeManage = new SetHomeManage(homeRepository, audentiaPlayersManagersProvider.rolesRepository, worldNameFinder);
        this.homesProvide = new HomesProvide(homeRepository);
        this.bankSlotsProvide = new BankSlotsProvide(gamesInfosRepository, bankSlotsRepository);
        this.staffInventoryOpen = new StaffInventoryOpen(audentiaPlayersManagersProvider.rolesRepository, staffInventoryOpener, worldPlayerFinder);
        this.scoreboardManage = new ScoreboardManage(audentiaPlayersManagersProvider.teamsManager, audentiaPlayersManagersProvider.rolesRepository, gamesInfosRepository, timeProvider, eventsRepository, scoreboardsRepository);
        this.eventProvider = new EventProvider(eventsRepository, gamesInfosRepository);
        this.npcInteract = new NpcInteract(bankNpcProvider, bankInventoryOpen, shopInventoryOpen, netherShopInventoryOpen, netherNpcRepository);
        this.npcSpawn = new NpcSpawn(npcSpawner, npcRepository, worldNpcFinder);
        this.borderCreate = new BorderCreate(borderInfosRepository, borderSpawner);
        rolesRepository = audentiaPlayersManagersProvider.rolesRepository;
    }

}
