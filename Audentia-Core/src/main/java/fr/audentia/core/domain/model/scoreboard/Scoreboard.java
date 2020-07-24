package fr.audentia.core.domain.model.scoreboard;

import java.util.List;
import java.util.Objects;

public class Scoreboard {

    public final String header;
    public final List<String> content;
    public final String footer;

    public Scoreboard(ScoreboardBuilder builder) {
        this.header = builder.getHeader();
        this.content = builder.getContent();
        this.footer = builder.getFooter();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scoreboard that = (Scoreboard) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(content, that.content) &&
                Objects.equals(footer, that.footer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, content, footer);
    }

}
