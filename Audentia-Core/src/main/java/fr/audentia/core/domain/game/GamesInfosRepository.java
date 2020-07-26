package fr.audentia.core.domain.game;

import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.players.domain.model.Day;

public interface GamesInfosRepository {

    Day getDay();

    EmeraldsLimitation getEmeraldsLimitation(Day day);

    BankSlots getBankOpenSlots(Day day);

    long getStartTimeInSeconds();

    long getGameDurationInSeconds();

    GameState getGameState();

}
