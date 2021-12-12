package fr.uge.splendor.object;

import java.util.List;

/**
 * Interface corresponding of the deck of the game
 * @param <T> type of element in the deck
 */
public interface Deck<T> extends Displayable {

    /**
     * This method add the specified element to the deck
     * @param t to add to the deck
     */
    void add(T t);

    /**
     * This method draw number of element in the deck and return them in a list
     * @param number of card to draw
     * @return list of drawn element
     */
    List<T> drawCards(int number);

    /**
     * This method clear the deck
     */
    void clear();

    /**
     * This method shuffle the deck
     */
    void shuffle();

    /**
     * This method return the number of element to draw for the start of the game
     * @param size of player list
     * @return the number of element to draw for the start of the game
     */
    int getNumberToDraw(int size);

    /**
     * This method draw a card from the deck
     * @return drawn card
     */
    T draw();
}
