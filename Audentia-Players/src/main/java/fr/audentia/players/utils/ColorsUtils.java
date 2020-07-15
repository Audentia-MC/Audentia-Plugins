package fr.audentia.players.utils;

import java.awt.*;
import java.util.Arrays;

public class ColorsUtils {

    private ColorsUtils() {
    }

    public static Color fromText(String french) {

        switch (french.toLowerCase()) {

            case "blue":
                return Color.BLUE;

            case "black":
                return Color.BLACK;

            case "red":
                return Color.RED;

            case "green":
                return Color.GREEN;

            case "gray":
                return Color.GRAY;

            case "yellow":
                return Color.YELLOW;

            case "pink":
                return Color.PINK;

            case "cyan":
                return Color.CYAN;

            case "white":
            default:
                return Color.WHITE;

        }

    }

    public static boolean isInvalidForTeam(Color color) {
        return Arrays.asList(Color.WHITE, Color.BLACK).contains(color);
    }

    public static String fromColor(Color color) {

        if (Color.BLUE == color) return "<blue";

        if (Color.BLACK == color) return "<black";

        if (Color.RED == color) return "<red";

        if (Color.GREEN == color) return "<green";

        if (Color.GRAY == color) return "<gray";

        if (Color.YELLOW == color) return "<yellow";

        if (Color.PINK == color) return "<pink";

        if (Color.CYAN == color) return "<cyan";

        return "<white";
    }

}
