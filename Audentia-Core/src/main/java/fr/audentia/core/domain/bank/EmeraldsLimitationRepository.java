package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.players.domain.model.Day;

public interface EmeraldsLimitationRepository {

    EmeraldsLimitation getEmeraldsLimitation(Day day);

}
