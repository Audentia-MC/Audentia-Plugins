package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.players.domain.model.Day;

public interface BankSlotsRepository {

    BankSlots getBankOpenSlots(Day day);

}
