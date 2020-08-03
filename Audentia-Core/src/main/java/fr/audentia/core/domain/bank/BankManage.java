package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;

import java.util.Optional;
import java.util.UUID;

public class BankManage {

    private final BalanceManage balanceManage;
    private final GamesInfosRepository gamesInfosRepository;
    private final BankSlotsRepository bankSlotsRepository;
    private final TimeProvider timeProvider;
    private final TeamsRepository teamsRepository;

    public BankManage(BalanceManage balanceManage, GamesInfosRepository gamesInfosRepository, BankSlotsRepository bankSlotsRepository, TimeProvider timeProvider, TeamsRepository teamsRepository) {
        this.balanceManage = balanceManage;
        this.gamesInfosRepository = gamesInfosRepository;
        this.bankSlotsRepository = bankSlotsRepository;
        this.timeProvider = timeProvider;
        this.teamsRepository = teamsRepository;
    }

    public String depositEmeralds(UUID playerUUID, int count) {

        Day day = gamesInfosRepository.getDay();
        Optional<Team> optionalTeam = teamsRepository.getTeamOfPlayer(playerUUID);

        if (!optionalTeam.isPresent()) {
            return "<error>Votre groupe ne peut pas accéder à la banque.";
        }

        if (!bankSlotsRepository.getBankOpenSlots(day).isOpen(timeProvider.getHour())) {
            return "<error>La banque est fermée.";
        }

        Team team = optionalTeam.get();
        if (!team.transfers.containsKey(day)) {
            team.transfers.put(day, new DayTransfers(0));
        }

        int finalCount = gamesInfosRepository.getEmeraldsLimitation(day).computePossibleTransfer(count, team.transfers.get(day));

        if (finalCount == -1) {
            return "<error>Vous avez déjà déposé le maximum d'émeraudes possible pour aujourd'hui.";
        }

        return balanceManage.addToBalance(playerUUID, finalCount);
    }

}
