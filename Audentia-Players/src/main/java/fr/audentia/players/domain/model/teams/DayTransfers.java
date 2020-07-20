package fr.audentia.players.domain.model.teams;

public class DayTransfers {

    public final int value;

    public DayTransfers(int value) {
        this.value = value;
    }

    public DayTransfers add(int count) {
        return new DayTransfers(value + count);
    }

}
