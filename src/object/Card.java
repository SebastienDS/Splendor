package object;

import java.util.Set;

/**
 * Interface representing all the cards of the game
 */
public interface Card extends Displayable {
    /**
     * This method return the name of the card
     * @return the name of the card
     */
    String getName();

    /**
     * This method return the corresponding price of the specified token
     * @param token specified token
     * @return corresponding price of the specified token
     */
    int getCost(Token token);

    /**
     * This method return a set of all the token needed to buy it
     * @return set of all the token needed to buy it
     */
    Set<Token> getTokens();

    /**
     * This method return the prestige the card gives
     * @return prestige the card gives
     */
    int getPrestige();

    /**
     * This method return the bonus the card gives
     * @return the bonus the card gives
     */
    default Token getBonus() {
        return null;
    }
}
