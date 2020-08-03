package fr.audentia.core.domain.game;

import java.util.Arrays;

public enum GameState {

    WAITING("waiting"),
    PLAYING("playing"),
    PAUSED("waiting"),
    ENDED("ended");

    private final String text;

    GameState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static GameState fromText(String text) {
        return Arrays.stream(values())
                .filter(state -> state.text.equalsIgnoreCase(text))
                .findAny()
                .orElse(WAITING);
    }

}
