package fr.audentia.core.infrastructure.bank;

import fr.audentia.core.domain.bank.TimeProvider;

import java.time.*;

public class DefaultTimeProvider implements TimeProvider {

    @Override
    public int getHour() {
        return LocalTime.now().getHour();
    }

    @Override
    public long getActualTimeInSeconds() { // TO DO : test if seconds matches with france
        return Instant.now().getEpochSecond();
    }

}
