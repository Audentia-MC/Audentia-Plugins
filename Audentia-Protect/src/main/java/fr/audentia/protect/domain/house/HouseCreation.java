package fr.audentia.protect.domain.house;

import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;
import fr.audentia.protect.domain.model.house.HouseCreationModel;

import java.util.UUID;

public class HouseCreation {

    private final RolesRepository rolesRepository;
    private final HouseCreationRepository houseCreationRepository;
    private final HouseRepository houseRepository;

    public HouseCreation(RolesRepository rolesRepository, HouseCreationRepository houseCreationRepository, HouseRepository houseRepository) {
        this.rolesRepository = rolesRepository;
        this.houseCreationRepository = houseCreationRepository;
        this.houseRepository = houseRepository;
    }

    public String startCreation(UUID playerUUID) {

        if (!rolesRepository.getRole(playerUUID).staff) {
            return "<error>Vous n'avez pas la permission de créer une nouvelle maison.";
        }

        houseCreationRepository.startCreation(playerUUID);
        return "<success>Création d'une nouvelle maison lancée, cliquez sur le bloc où apparaitra le panneau.";
    }

    public String onInteract(UUID playerUUID, Location location, char blockFace) {

        if (!houseCreationRepository.isRunningCreation(playerUUID)) {
            return "";
        }

        HouseCreationModel creation = houseCreationRepository.getCreation(playerUUID);

        if (creation.signLocation == null) {
            creation = creation.withSignLocation(location);
            creation = creation.withSignFace(blockFace);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "<success>Position du panneau enregistrée, entrez le niveau de la maison (1, 2 ou 3).";
        }

        if (creation.level == -1 || creation.price == -1) {
            return "";
        }

        creation = creation.addBloc(location);
        houseCreationRepository.saveCreation(playerUUID, creation);

        if (creation.tempLocation == null) {
            return "<success>Espace enregistré, cliquez sur le premier bloc du prochain espace ou entrez n'importe quoi dans le chat pour finir la création de la maison.";
        }

        return "<success>Position 1 enregistrée, cliquez sur le 2e bloc.";
    }

    public String onChat(UUID playerUUID, String entry) {

        if (!houseCreationRepository.isRunningCreation(playerUUID)) {
            return "";
        }

        HouseCreationModel creation = houseCreationRepository.getCreation(playerUUID);

        if (creation.signLocation == null) {
            return "<error>Cliquez sur le bloc où apparaitra le panneau";
        }

        if (creation.level == -1) {
            if (!entry.matches("[1-3]")) {
                return "<error>Entrez un nombre valide (1, 2 ou 3).";
            }

            int intLevel = Integer.parseInt(entry);
            creation = creation.withLevel(intLevel);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "<success>Niveau enregistré, entrez le prix de la maison.";
        }

        if (creation.price == -1) {
            if (!entry.matches("[0-9]+")) {
                return "<error>Entrez le prix de la maison.";
            }

            int price = Integer.parseInt(entry);
            creation = creation.withPrice(price);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "<success>Prix enregistré, cliquez sur le premier bloc d'un espace de la maison.";
        }

        if (creation.blocs.isEmpty()) {
            return "<error>Il faut au moins un espace pour la maison.";
        }

        House house = new House(creation);
        houseRepository.registerNewHouse(house);
        houseCreationRepository.stopCreation(playerUUID);
//        signsManage.forceReloadAllSigns();

        return "<success>Création d'une nouvelle maison effectuée avec succès. /reloadsigns pour charger le panneau.";
    }

}
