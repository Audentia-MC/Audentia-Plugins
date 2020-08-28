package fr.audentia.players.domain.model.tablist;

public class PlayerTabList {

    public final String header;
    public final String footer;
    public final String prefix;
    public final String team;

    public PlayerTabList(String header, String footer, String prefix, String team) {
        this.header = header;
        this.footer = footer;
        this.prefix = prefix;
        this.team = team;
    }

}
