package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.players.domain.model.Day;

public class BankSlotsProvide {

    private final GamesInfosRepository gamesInfosRepository;
    private final BankSlotsRepository bankSlotsProvider;

    public BankSlotsProvide(GamesInfosRepository gamesInfosRepository, BankSlotsRepository bankSlotsProvider) {
        this.gamesInfosRepository = gamesInfosRepository;
        this.bankSlotsProvider = bankSlotsProvider;
    }

    public String getBankSlots() {

        Day day = gamesInfosRepository.getDay();
        BankSlots slots = bankSlotsProvider.getBankOpenSlots(day);

        return "#FCB91FLes horaires d'ouverture de la banque pour aujourd'hui sont : " + slots.toString() + ".";
    }

}
