package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;

import java.util.List;
import java.util.UUID;

public class HomesProvide {

    private final HomeRepository homeRepository;

    public HomesProvide(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public String getHomes(UUID playerUUID) {

        List<Home> homes = homeRepository.getHomes(playerUUID);

        if (homes.isEmpty()) {
            return "<error>Vous n'avez aucun home défini.";
        }

        return "<success>Liste de vos homes :" + buildHomesMessage(homes);
    }

    private StringBuilder buildHomesMessage(List<Home> homes) {
        StringBuilder message = new StringBuilder();

        for (Home home : homes) {

            message.append("\n")
                    .append(home.number)
                    .append(", ")
                    .append(home.name)
                    .append(", ")
                    .append("- x: ")
                    .append(home.x)
                    .append(", y: ")
                    .append(home.y)
                    .append(", z: ")
                    .append(home.z);

        }

        return message;
    }

}
