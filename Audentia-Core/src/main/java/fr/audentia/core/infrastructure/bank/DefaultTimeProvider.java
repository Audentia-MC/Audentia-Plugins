package fr.audentia.core.infrastructure.bank;

import fr.audentia.core.domain.bank.TimeProvider;

import java.time.*;

public class DefaultTimeProvider implements TimeProvider {

    @Override
    public int getHour() {
        return ZonedDateTime.now().getHour();
    }

    @Override
    public LocalDateTime getActualTime() { // TO DO : test if seconds matches with france
        return ZonedDateTime.now().toLocalDateTime();
    }

}
