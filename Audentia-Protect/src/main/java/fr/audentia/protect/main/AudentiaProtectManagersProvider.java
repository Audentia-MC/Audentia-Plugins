package fr.audentia.protect.main;

import fr.audentia.core.main.AudentiaCoreManagersProvider;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;
import fr.audentia.protect.domain.house.*;
import fr.audentia.protect.domain.portals.NetherLocationRepository;
import fr.audentia.protect.domain.portals.NetherPortalProtection;
import fr.audentia.protect.domain.portals.PortalCreateCheck;
import fr.audentia.protect.infrastructure.house.DefaultHouseCreationRepository;
import fr.audentia.protect.infrastructure.house.SpigotSignUtils;
import fr.audentia.protect.infrastructure.repositories.DefaultBuyHouseClicksRepository;
import fr.audentia.protect.infrastructure.repositories.MariaDbHouseRepository;
import fr.audentia.protect.infrastructure.repositories.portal.TOMLNetherLocationRepository;

public class AudentiaProtectManagersProvider {

    public final HouseAction houseAction;
    public final BuyHouseAction buyHouseAction;
    public final PortalCreateCheck portalCreateCheck;
    public final SignsManage signsManage;
    public final HouseCreation houseCreation;
    public final NetherPortalProtection netherPortalProtection;

    public AudentiaProtectManagersProvider(AudentiaPlayersManagersProvider audentiaPlayersManagersProvider, AudentiaCoreManagersProvider audentiaCoreManagersProvider, String path) {

        HouseRepository houseRepository = new MariaDbHouseRepository(audentiaPlayersManagersProvider.databaseConnection);
        BuyHouseClicksRepository clicksRepository = new DefaultBuyHouseClicksRepository();
        NetherLocationRepository netherLocationRepository = new TOMLNetherLocationRepository(path);
        HouseCreationRepository houseCreationRepository = new DefaultHouseCreationRepository();

        HouseAction houseAction1 = new HouseAction(houseRepository, audentiaPlayersManagersProvider.rolesRepository, audentiaPlayersManagersProvider.teamsManager, audentiaCoreManagersProvider.balanceManage, null, audentiaCoreManagersProvider.cityInfosRepository);

        SignUtils signUtils = new SpigotSignUtils(houseAction1);

        houseAction = new HouseAction(houseRepository, audentiaPlayersManagersProvider.rolesRepository, audentiaPlayersManagersProvider.teamsManager, audentiaCoreManagersProvider.balanceManage, signUtils, audentiaCoreManagersProvider.cityInfosRepository);
        buyHouseAction = new BuyHouseAction(clicksRepository);
        portalCreateCheck = new PortalCreateCheck(audentiaPlayersManagersProvider.rolesRepository, netherLocationRepository);
        signsManage = new SignsManage(audentiaPlayersManagersProvider.rolesRepository, houseRepository, signUtils);
        houseCreation = new HouseCreation(audentiaPlayersManagersProvider.rolesRepository, houseCreationRepository, houseRepository);
        netherPortalProtection = new NetherPortalProtection(audentiaPlayersManagersProvider.rolesRepository, netherLocationRepository);
    }

}
