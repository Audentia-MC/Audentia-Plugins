package fr.audentia.protect.main;

import fr.audentia.core.main.AudentiaCoreManagersProvider;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;
import fr.audentia.players.utils.DataBaseConfigurationLoader;
import fr.audentia.protect.domain.house.*;
import fr.audentia.protect.domain.portals.NetherLocationRepository;
import fr.audentia.protect.domain.portals.PortalCreateCheck;
import fr.audentia.protect.infrastructure.house.SpigotSignUtils;
import fr.audentia.protect.infrastructure.repositories.DefaultBuyHouseClicksRepository;
import fr.audentia.protect.infrastructure.repositories.MariaDbHouseRepository;
import fr.audentia.protect.infrastructure.repositories.portal.TOMLNetherLocationRepository;

public class AudentiaProtectManagersProvider {

    public final HouseAction houseAction;
    public final BuyHouseAction buyHouseAction;
    public final PortalCreateCheck portalCreateCheck;
    public final SignsManage signsManage;

    public AudentiaProtectManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider, AudentiaCoreManagersProvider audentiaCoreManagersProvider, String path) {

        DatabaseConnection databaseConnection = DataBaseConfigurationLoader.loadConnection(path);

        HouseRepository houseRepository = new MariaDbHouseRepository(databaseConnection);
        BuyHouseClicksRepository clicksRepository = new DefaultBuyHouseClicksRepository();
        NetherLocationRepository netherLocationRepository = new TOMLNetherLocationRepository(path);


        houseAction = new HouseAction(houseRepository, audentiaPlayersManagersProvider.rolesRepository, audentiaPlayersManagersProvider.teamsManager, audentiaCoreManagersProvider.balanceManage);

        SignUtils signUtils = new SpigotSignUtils(houseAction);

        buyHouseAction = new BuyHouseAction(clicksRepository);
        portalCreateCheck = new PortalCreateCheck(audentiaPlayersManagersProvider.rolesRepository, netherLocationRepository);
        signsManage = new SignsManage(audentiaPlayersManagersProvider.rolesRepository, houseRepository, signUtils);
    }

}
