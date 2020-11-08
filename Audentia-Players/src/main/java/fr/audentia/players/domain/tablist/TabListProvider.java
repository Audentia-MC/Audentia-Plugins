package fr.audentia.players.domain.tablist;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.tablist.PlayerTabList;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.players.utils.ColorsUtils;

import java.awt.*;
import java.util.UUID;

public class TabListProvider {

    private final RolesRepository rolesRepository;
    private final TeamsManager teamsManager;

    public TabListProvider(RolesRepository rolesRepository, TeamsManager teamsManager) {
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
    }

    public PlayerTabList getTabList(UUID playerUUID) {

        String header = "\n<blue>     >> Cité d'Audentia <<     \n";
        String footer = "";

        Role role = rolesRepository.getRole(playerUUID);
        Team team = teamsManager.getTeam(playerUUID);

        Color color = role.color != Color.white ? role.color : team.color;

        String number = String.valueOf(role.echelon);
        StringBuilder toAdd = new StringBuilder();

        for (int i = 0; i < 4 - number.length(); i++) {
            toAdd.append("0");
        }

        number = toAdd + number;

        return new PlayerTabList(header, footer, "&" + ColorsUtils.fromColorToHexadecimal(color) + "[" + role.name + "] ", number);
    }

}
