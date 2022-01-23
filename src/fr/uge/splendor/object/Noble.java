package fr.uge.splendor.object;

import java.util.Objects;

/**
 * This class represent the noble card used during the game.
 */
public record Noble(TokenManager cost, String name, int prestige) {

    /**
     * Name of the type of the card.
     */
    public static final String TYPE = "Noble";

    /**
     * Create an Instance of Noble
     * @param cost of the card
     * @param name of the card
     * @param prestige given by the card
     */
    public Noble {
        if (prestige < 0) throw new IllegalArgumentException("Prestige must be >= 0");

        Objects.requireNonNull(cost);
        Objects.requireNonNull(name);
    }

}
