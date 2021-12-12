package fr.uge.splendor.object;

/**
 * Enum representing all the token of the game
 */
public enum Token {
    /**
     * Token representing emerald
     */
    EMERALD,
    /**
     * Token representing sapphire
     */
    SAPPHIRE,
    /**
     * Token representing ruby
     */
    RUBY,
    /**
     * Token representing diamond
     */
    DIAMOND,
    /**
     * Token representing onyx
     */
    ONYX,
    /**
     * Token representing gold
     */
    GOLD;

    /**
     * This method return the table of all the token representing card
     * @return table of all the token representing card
     */
    public static Token[] cardValues() {
        return new Token[] { EMERALD, SAPPHIRE, RUBY, DIAMOND, ONYX };
    }
}
