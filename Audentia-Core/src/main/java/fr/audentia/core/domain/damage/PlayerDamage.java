package fr.audentia.core.domain.damage;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.location.Location;
import fr.audentia.core.domain.protect.CityInfosRepository;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.ColiseumKill;
import fr.audentia.players.domain.model.teams.ColiseumKills;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerDamage {

    private final TeamsManager teamsManager;
    private final RolesRepository rolesRepository;
    private final BalanceManage balanceManage;
    private final ColiseumLocationRepository coliseumLocationRepository;
    private final GamesInfosRepository gamesInfosRepository;
    private final TimeProvider timeProvider;
    private final TimeProtectionAtStartProvider timeProtectionAtStartProvider;
    private final CityInfosRepository cityInfosRepository;

    public PlayerDamage(TeamsManager teamsManager, RolesRepository rolesRepository, BalanceManage balanceManage, ColiseumLocationRepository coliseumLocationRepository, GamesInfosRepository gamesInfosRepository, TimeProvider timeProvider, TimeProtectionAtStartProvider timeProtectionAtStartProvider, CityInfosRepository cityInfosRepository) {
        this.teamsManager = teamsManager;
        this.rolesRepository = rolesRepository;
        this.balanceManage = balanceManage;
        this.coliseumLocationRepository = coliseumLocationRepository;
        this.gamesInfosRepository = gamesInfosRepository;
        this.timeProvider = timeProvider;
        this.timeProtectionAtStartProvider = timeProtectionAtStartProvider;
        this.cityInfosRepository = cityInfosRepository;
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

        Team team = teamsManager.getTeamOfPlayer(damagerUUID);
        Day day = gamesInfosRepository.getDay();

        if (!team.coliseumKills.containsKey(day)) {
            team.coliseumKills.put(day, new ColiseumKills(new ArrayList<>()));
        }

        int kills = team.coliseumKills.get(day).kills.size();

        if (kills < 10 && location.distanceSquared(coliseumLocation) <= Math.pow(coliseumSize, 2)) {
            percentage = 0.05f;
            team.coliseumKills.get(day).kills.add(new ColiseumKill(damagerUUID, damagedUUID, (int) (System.currentTimeMillis() / 100)));
            teamsManager.saveTeam(team);
        }

        double toMove = Math.ceil(Integer.parseInt(damagedBalance) * percentage);

        balanceManage.forceAddToBalance(damagerUUID, (int) toMove);
        balanceManage.forceRemoveFromBalance(damagedUUID, (int) toMove);
    }

    public boolean canBeDamaged(UUID damagedUUID, UUID damagerUUID, Location location) {

        if (gamesInfosRepository.getStartTimeInSeconds() == -1) {
            return false;
        }

        long actualTimeInGame = timeProvider.getActualTimeInSeconds() - gamesInfosRepository.getStartTimeInSeconds();
        int minutesOfProtection = timeProtectionAtStartProvider.getMinutesOfProtection();

        if (actualTimeInGame / 60 < minutesOfProtection) {
            return false;
        }

        Location cityLocation = cityInfosRepository.getCityLocation();
        double radiusSquared = Math.pow(cityInfosRepository.getCityRadius(), 2);
        double distanceSquared = location.distanceSquared2D(cityLocation);

        boolean temp = distanceSquared <= radiusSquared;

        if (temp) {

            Location coliseumLocation = coliseumLocationRepository.getColiseumLocation();
            double coliseumSizeSquared = Math.pow(coliseumLocationRepository.getColiseumSize(), 2);

            if (location.distanceSquared2D(coliseumLocation) > coliseumSizeSquared) {
                temp = false;
            }

        }

        if (temp) {
            return false;
        }

        Team teamOfPlayer = teamsManager.getTeamOfPlayer(damagerUUID);

        return !teamsManager.getTeamOfPlayer(damagedUUID).equals(teamOfPlayer) && teamOfPlayer.color != Color.BLACK;
    }

}
