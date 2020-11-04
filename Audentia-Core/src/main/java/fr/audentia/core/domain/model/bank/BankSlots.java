package fr.audentia.core.domain.model.bank;

import java.util.List;
import java.util.stream.Collectors;

public class
BankSlots {

    private final List<Slot> slots;

    public BankSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public boolean isOpen(int hour) {

        return slots.stream()
                .anyMatch(slot -> slot.start <= hour && hour < slot.end);
    }

    @Override
    public String toString() {
        return slots.stream()
                .map(Slot::toString)
                .collect(Collectors.joining(", "));
    }
}
