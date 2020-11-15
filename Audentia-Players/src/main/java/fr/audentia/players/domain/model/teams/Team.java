package fr.audentia.players.domain.model.teams;

import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class Team {

    public final Color color;
    public final Balance balance;
    public final Map<Day, DayTransfers> transfers;
    public final Map<Day, ColiseumKills> coliseumKills;
    public final String name;
    public final long houseId;

    public Team(Color color, Balance balance, Map<Day, DayTransfers> transfers, Map<Day, ColiseumKills> coliseumKills, String name, long houseId) {
        this.color = color;
        this.balance = balance;
        this.transfers = transfers;
        this.coliseumKills = coliseumKills;
        this.name = name;
        this.houseId = houseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(color, team.color) &&
                Objects.equals(balance, team.balance);
    }

}
