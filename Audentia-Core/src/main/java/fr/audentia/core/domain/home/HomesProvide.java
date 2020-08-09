package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;

import java.util.List;
import java.util.UUID;

public class HomesProvide {

    private final HomeRepository homeRepository;

    public HomesProvide(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public String getHomes(UUID playerUUID) {

        List<HomeLocation> homeLocations = homeRepository.getHomes(playerUUID);

        if (homeLocations.isEmpty()) {
            return "<error>Vous n'avez aucun home défini.";
        }

        return "<success>Liste de vos homes :" + buildHomesMessage(homeLocations);
    }

    private StringBuilder buildHomesMessage(List<HomeLocation> homeLocations) { // TODO: test \n
        StringBuilder homes = new StringBuilder();

        for (int i = 0; i < homeLocations.size(); i++) {

            HomeLocation homeLocation = homeLocations.get(i);

            homes.append("\n")
                    .append(i + 1)
                    .append(" - x: ")
                    .append(homeLocation.x)
                    .append(", y: ")
                    .append(homeLocation.y)
                    .append(", z: ")
                    .append(homeLocation.z);

        }

        return homes;
    }

}
