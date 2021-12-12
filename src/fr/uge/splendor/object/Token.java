package fr.uge.splendor.object;

/**
 * Enum representing all the token of the game
 */
public enum Token {
    EMERALD,
    SAPPHIRE,
    RUBY,
    DIAMOND,
    ONYX,
    GOLD;

    /**
     * This method return the table of all the token representing card
     * @return table of all the token representing card
     */
    public static Token[] cardValues() {
        return new Token[] { EMERALD, SAPPHIRE, RUBY, DIAMOND, ONYX };
    }
}
