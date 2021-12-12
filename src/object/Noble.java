package object;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static object.Utils.space;

/**
 * This class represent the noble card used during the game.
 */
public class Noble implements Card {

    /**
     * Name of the type of the card.
     */
    public static final String TYPE = "Noble";

    /**
     * Field that represent the card
     */
    private final Card card;

    /**
     * Table of all the line of the display of the card
     */
    private final String[] display;
    /**
     * List of all string corresponding to the price of the card
     */
    private final List<String> price;

    /**
     * Create an Instance of Noble
     * @param cost of the card
     * @param name of the card
     * @param image of the card
     * @param prestige given by the card
     */
    public Noble(Map<Token, Integer> cost, String name, String image, int prestige) {
        card = new BasicCard(cost, name, image, prestige);
        price = cost.keySet().stream().map(token -> token.name() + " : " + cost.get(token)).toList();
        display = stringDisplay();
    }

    /**
     * This method return the string of the display of the card for the i-th line
     * @param i th line
     * @return string of the display of the card for the i-th line
     * @Override getDisplay of interface displayable
     */
    @Override
    public String getDisplay(int i) {
        return display[i];
    }

    /**
     * This method return the name of the card
     * @return the name of the card
     * @Override getName of interface Card
     */
    @Override
    public String getName() {
        return card.getName();
    }

    /**
     * This method return the corresponding price of the specified token
     * @param token specified token
     * @return corresponding price of the specified token
     * @Override getCost of interface Card
     */
    @Override
    public int getCost(Token token) {
        return card.getCost(token);
    }

    /**
     * This method return a set of all the token needed to buy it
     * @return set of all the token needed to buy it
     * @Override getTokens of interface Card
     */
    @Override
    public Set<Token> getTokens() {
        return card.getTokens();
    }

    /**
     * This method return the prestige the card gives
     * @return prestige the card gives
     * @Override getPrestige of interface Card
     */
    @Override
    public int getPrestige() {
        return card.getPrestige();
    }

    /**
     * This method return the bonus the card gives
     * @return the bonus the card gives
     * @Override getBonus of interface Card
     */
    @Override
    public Token getBonus() {
        return card.getBonus();
    }

    /**
     * This method create the tab of all string to display to draw the card and return it
     * @return the tab of all string to display to draw the card
     */
    private String[] stringDisplay() {
        var prestigeLength = String.valueOf(getPrestige()).length() + "Prestige: ".length();
        var priceLength = "Price: ".length();
        var centerName = getCenterName();
        var name = getName();
        var string = new String[] {
                " "+ "_".repeat(Constants.MAX_LENGTH) +" ",
                "|" + space(centerName) + name + space((name.length() % 2 == 1)? centerName + 1 : centerName) + "|",
                "|Prestige: " + getPrestige() +space(Constants.MAX_LENGTH - prestigeLength) + "|",
                "|" + space(Constants.MAX_LENGTH) + "|",
                "|Price: " + space(Constants.MAX_LENGTH - priceLength) + "|",
                "|" + stringPrice(0) + space(Constants.MAX_LENGTH - stringPrice(0).length()) + "|",
                "|" + stringPrice(1) + space(Constants.MAX_LENGTH - stringPrice(1).length()) + "|",
                "|" + stringPrice(2) + space(Constants.MAX_LENGTH - stringPrice(2).length()) + "|",
                "|" + stringPrice(3) + space(Constants.MAX_LENGTH - stringPrice(3).length()) + "|",
                " " + "-".repeat(Constants.MAX_LENGTH) + " "
        };
        return string;
    }

    /**
     * This method return the string value of the i-th price of the card or an empty list if the price doesn't exist
     * @param i th price
     * @return string value of the i-th price of the card or an empty list if the price doesn't exist
     */
    private String stringPrice(int i){
        return (card.getTokens().size() >  i)? price.get(i) : "";
    }

    /**
     * This method return the length needed to add to center the name
     * @return length needed to add to center the name
     */
    private int getCenterName(){
        return (Constants.MAX_LENGTH - card.getName().length()) / 2;
    }
}
