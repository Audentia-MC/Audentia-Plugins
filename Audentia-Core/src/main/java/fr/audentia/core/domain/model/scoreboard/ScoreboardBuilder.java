package fr.audentia.core.domain.model.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardBuilder {

    private String header = "";
    private List<String> content = new ArrayList<>();
    private String footer = "";

    private ScoreboardBuilder() {
    }

    public static ScoreboardBuilder aScoreboard() {
        return new ScoreboardBuilder();
    }

    public ScoreboardBuilder withHeader(String header) {
        this.header = header;
        return this;
    }

    public ScoreboardBuilder withContent(List<String> content) {
        this.content = content;
        return this;
    }

    public ScoreboardBuilder addContent(String content) {
        this.content.add(content);
        return this;
    }

    public ScoreboardBuilder withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public Scoreboard build() {
        return new Scoreboard(this);
    }

    public String getHeader() {
        return header;
    }

    public List<String> getContent() {
        return content;
    }

    public String getFooter() {
        return footer;
    }

}
