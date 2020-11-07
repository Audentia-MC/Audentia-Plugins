package fr.audentia.core.infrastructure.scoreboard;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.scoreboard.EventsRepository;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TOMLEventsRepository implements EventsRepository {

    private final String filePath;

    public TOMLEventsRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ZonedDateTime getNextEvent() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextEvent = null;

        for (String date : fileConfig.getOrElse("game.events", new ArrayList<String>())) {

            ZonedDateTime eventDate = ZonedDateTime.parse(date + "+01:00[Europe/Paris]");

            if (eventDate.isAfter(now) && (nextEvent == null || eventDate.isBefore(nextEvent))) {
                nextEvent = eventDate;
            }

        }

        fileConfig.close();

        return nextEvent;
    }

}
