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
    private final SignsManage signsManage;

    public HouseCreation(RolesRepository rolesRepository, HouseCreationRepository houseCreationRepository, HouseRepository houseRepository, SignsManage signsManage) {
        this.rolesRepository = rolesRepository;
        this.houseCreationRepository = houseCreationRepository;
        this.houseRepository = houseRepository;
        this.signsManage = signsManage;
    }

    public String startCreation(UUID playerUUID) {

        if (!rolesRepository.getRole(playerUUID).staff) {
            return "<error>Vous n'avez pas la permission de créer une nouvelle maison.";
        }

        houseCreationRepository.startCreation(playerUUID);
        return "<success>Création d'une nouvelle maison lancée, cliquez sur le premier bloc.";
    }

    public String onInteract(UUID playerUUID, Location location, char blockFace) {

        if (!houseCreationRepository.isRunningCreation(playerUUID)) {
            return "";
        }

        HouseCreationModel creation = houseCreationRepository.getCreation(playerUUID);

        if (!creation.location1.isPresent()) {
            creation = creation.withLocation1(location);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "<success>Position 1 enregistrée, cliquez sur le 2e bloc.";
        }

        if (!creation.location2.isPresent()) {
            creation = creation.withLocation2(location);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "<success>Position 2 enregistrée, cliquez sur l'emplacement du panneau en étant face au côté où il apparaitra.";
        }

        if (!creation.signLocation.isPresent()) {
            creation = creation.withSignLocation(location);
            creation = creation.withSignFace(blockFace);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "<success>Position du panneau enregistrée, entrez le niveau de la maison (entre 6 compris et 9 compris).";
        }

        return "";
    }

    public String onChat(UUID playerUUID, String entry) { // TODO: add price

        if (!houseCreationRepository.isRunningCreation(playerUUID)) {
            return "";
        }

        HouseCreationModel creation = houseCreationRepository.getCreation(playerUUID);

        if (creation.level == -1) {
            if (!entry.matches("[6-9]")) {
                return "<error>Entrez un nombre entre 6 compris et 9 compris.";
            }

            int intLevel = Integer.parseInt(entry);
            creation = creation.withLevel(intLevel);
            houseCreationRepository.saveCreation(playerUUID, creation);
            return "";
        }

        if (!entry.matches("[0-9]+")) {
            return "<error>Entrez le prix de la maison.";
        }

        int price = Integer.parseInt(entry);
        creation = creation.withPrice(price);

        House house = new House(creation);
        houseRepository.registerNewHouse(house);
        houseCreationRepository.stopCreation(playerUUID);
        signsManage.forceReloadAllSigns();
        return "<success>Création d'une nouvelle maison effectuée avec succès.";
    }

}
