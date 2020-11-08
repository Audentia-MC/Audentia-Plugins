package fr.audentia.core.domain.damage;

import fr.audentia.players.domain.model.teams.ColiseumKill;

import java.awt.*;

public interface ColiseumKillsRepository {

    void registerKill(Color teamColor, ColiseumKill kill);

}
