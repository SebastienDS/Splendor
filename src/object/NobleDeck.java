package object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the deck with noble. It will be used so that only noble drawn at the start will be used
 * during the game
 */
public class NobleDeck implements Deck<Card> {

    /**
     * Field that represents the deck
     */
    private final Deck<Card> deck;

    /**
     * Create an instance of NobleDeck with the specified name
     * @param name given to the deck
     */
    public NobleDeck(String name) {
        deck = new BasicDeck<>(name);
    }

    /**
     * This method return the number of card that will be drawn at the start
     * @param playerSize number of player
     * @return number of card that will be drawn at the start
     * @Override getNumberToDraw of interface deck
     */
    @Override
    public int getNumberToDraw(int playerSize) {
        return playerSize + 1;
    }

    /**
     * This method draw a card
     * @return card drawn
     * @Override draw of interface deck
     */
    @Override
    public Card draw() {
        return deck.draw();
    }

    /**
     * This method draw number of card and return them on a list instance. The deck is cleared after drawing the card.
     * @param number of card to draw
     * @return list of card drawn from the deck
     * @Override drawCards of interface Deck
     */
    @Override
    public List<Card> drawCards(int number) {
        var list = deck.drawCards(number);
        deck.clear();
        return list;
    }

    /**
     * This method shuffle all the card of the deck
     * @Override shuffle of interface Deck
     */
    @Override
    public void shuffle() {
        deck.shuffle();
    }

    /**
     * This method clear all the card of the deck
     * @Override clear of interface Deck
     */
    @Override
    public void clear() {
        deck.clear();
    }

    /**
     * This method return the string of the display of the deck for the i-th line
     * @param i th line
     * @return string of the display of the deck for the i-th line
     * @Override getDisplay of interface displayable
     */
    @Override
    public String getDisplay(int i) {
        return "";
    }

    /**
     * This method add the specified card to the deck
     * @param card specified card
     * @Override add in interface Deck
     */
    @Override
    public void add(Card card) {
        var list = new ArrayList<Integer>();
        list.add(2);
        Objects.requireNonNull(card);
        deck.add(card);
    }
}
