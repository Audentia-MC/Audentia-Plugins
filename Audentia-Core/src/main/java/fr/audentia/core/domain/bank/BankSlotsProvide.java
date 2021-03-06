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

        String slotsDescription = slots.toString();

        if (slotsDescription.isEmpty()) {
            return "<error>La banque n'ouvrira pas aujourd'hui.";
        }

        return "&#FCB91FHoraires d'ouverture de la banque : " + slotsDescription + ".";
    }

}
