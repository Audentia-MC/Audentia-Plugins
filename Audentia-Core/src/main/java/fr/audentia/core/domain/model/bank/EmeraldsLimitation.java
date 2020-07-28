package fr.audentia.core.domain.model.bank;

import fr.audentia.players.domain.model.teams.DayTransfers;

public class EmeraldsLimitation {

    private final int limitation;

    public EmeraldsLimitation(int limitation) {
        this.limitation = limitation;
    }

    public int computePossibleTransfer(int count, DayTransfers dayTransfers) {

        if (limitation <= dayTransfers.value) {
            return -1;
        }

        if (dayTransfers.value + count > limitation) {
            return limitation - dayTransfers.value;
        }

        return count;
    }

}
