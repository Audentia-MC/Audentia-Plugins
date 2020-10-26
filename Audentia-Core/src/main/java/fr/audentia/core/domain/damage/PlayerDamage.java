package fr.audentia.core.domain.damage;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.model.location.Location;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.awt.*;
import java.util.UUID;

public class PlayerDamage {

    private final TeamsManager teamsManager;
    private final RolesRepository rolesRepository;
    private final BalanceManage balanceManage;
    private final ColiseumLocationRepository coliseumLocationRepository;

    public PlayerDamage(TeamsManager teamsManager, RolesRepository rolesRepository, BalanceManage balanceManage, ColiseumLocationRepository coliseumLocationRepository) {
        this.teamsManager = teamsManager;
        this.rolesRepository = rolesRepository;
        this.balanceManage = balanceManage;
        this.coliseumLocationRepository = coliseumLocationRepository;
    }

    public boolean canBeDamaged(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);
        return role.staff;
    }

    public void executeDeath(UUID damagedUUID, UUID damagerUUID, Location location) {

        String damagedBalance = balanceManage.getBalance(damagedUUID);

        float percentage = 0.01f;

        Location coliseumLocation = coliseumLocationRepository.getColiseumLocation();
        int coliseumSize = coliseumLocationRepository.getColiseumSize();

        if (location.distanceSquared(coliseumLocation) <= Math.pow(coliseumSize, 2)) {
            percentage = 0.05f;
        }

        double toMove = Math.ceil(Integer.parseInt(damagedBalance) * percentage);

        balanceManage.forceAddToBalance(damagerUUID, (int) toMove);
        balanceManage.forceRemoveFromBalance(damagedUUID, (int) toMove);
    }

    public boolean canBeDamaged(UUID damagedUUID, UUID damagerUUID) {

        Team teamOfPlayer = teamsManager.getTeamOfPlayer(damagerUUID);

        return !teamsManager.getTeamOfPlayer(damagedUUID).equals(teamOfPlayer) && teamOfPlayer.color != Color.BLACK;
    }

}
