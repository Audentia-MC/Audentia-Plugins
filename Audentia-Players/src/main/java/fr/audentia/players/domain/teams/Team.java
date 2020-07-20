package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.balance.Balance;

import java.awt.*;
import java.util.Objects;

public class Team {

    public final Color color;
    public final Balance balance;

    public Team(Color color, Balance balance) {
        this.color = color;
        this.balance = balance;
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
