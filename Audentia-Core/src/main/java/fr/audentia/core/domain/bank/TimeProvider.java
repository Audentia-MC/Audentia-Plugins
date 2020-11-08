package fr.audentia.core.domain.bank;

import java.time.LocalDateTime;

public interface TimeProvider {

    int getHour();

    LocalDateTime getActualTime();

}
