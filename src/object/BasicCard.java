package object;

import java.nio.file.Path;
import java.util.*;

/**
 * This class represent the basic of all cards used in the game
 */
class BasicCard implements Card {
    /**
     * Map with each token associated with their price if price is non-null(0)
     */
    private final Map<Token, Integer> cost;
    /**
     * Name of the card
     */
    private final String name;
    /**
     * Image of the card
     */
    private final Path image;
    /**
     * Prestige given by the card
     */
    private final int prestige;

    /**
     * Create an instance of BasicCard
     * @param map of the cost of the card
     * @param name of the card
     * @param image of the card
     * @param prestige given by the card
     */
    public BasicCard(Map<Token, Integer> map, String name, Path image, int prestige) {
        cost = new LinkedHashMap<>(Objects.requireNonNull(map));
        this.name = Objects.requireNonNull(name);
        this.image = Objects.requireNonNull(image);
        this.prestige = prestige;
    }

    /**
     * This method return the corresponding price of the specified token
     * @param token specified token
     * @return corresponding price of the specified token
     * @Override getCost of interface Card
     */
    @Override
    public int getCost(Token token) {
        return cost.get(token);
    }

    /**
     * This method return a set of all the token needed to buy it
     * @return set of all the token needed to buy it
     * @Override getTokens of interface Card
     */
    @Override
    public Set<Token> getTokens() {
        return cost.keySet();
    }

    /**
     * This method return the prestige the card gives
     * @return prestige the card gives
     */
    @Override
    public int getPrestige() {
        return prestige;
    }

    /**
     * This method return the name of the card
     * @return the name of the card
     * @Override getName of interface Card
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method return an empty string
     * @param i th line
     * @return empty string
     * @Override getDisplay of interface displayable
     */
    @Override
    public String getDisplay(int i) {
        return "";
    }
}
