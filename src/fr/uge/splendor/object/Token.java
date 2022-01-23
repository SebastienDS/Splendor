package fr.uge.splendor.object;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;

/**
 * Enum representing all the token of the game
 */
public enum Token {
    /**
     * Token representing no token :)
     */
    NONE(Color.MAGENTA),
    /**
     * Token representing emerald
     */
    EMERALD(Color.green),
    /**
     * Token representing sapphire
     */
    SAPPHIRE(Color.CYAN),
    /**
     * Token representing ruby
     */
    RUBY(Color.red),
    /**
     * Token representing diamond
     */
    DIAMOND(Color.white),
    /**
     * Token representing onyx
     */
    ONYX(Color.BLACK),
    /**
     * Token representing gold
     */
    GOLD(Color.YELLOW);

    private final Color color;

    Token(Color color) {
        this.color = color;
    }

    /**
     * This method return the table of all the token representing card
     * @return table of all the token representing card
     */
    public static Token[] cardValues() {
        return new Token[] { EMERALD, SAPPHIRE, RUBY, DIAMOND, ONYX };
    }

    public static boolean contains(String name) {
        return Arrays.stream(values()).anyMatch(token -> token.name().equals(name));
    }

    public Paint getColor() {
        return color;
    }
}
