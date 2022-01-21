package fr.uge.splendor.object;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents the deck of the game
 * @param <T> element of the deck
 */
public class Deck<T> {

    /**
     * Stack representing the deck
     */
    private final Deque<T> deck;

    /**
     * Create an instance of BasicDeck with the specified name
     */
    public Deck() {
        deck = new ArrayDeque<>();
    }

    /**
     * This method add the specified element to the deck
     * @param t element to add
     */
    public void add(T t) {
        Objects.requireNonNull(t);
        deck.add(t);
    }

    /**
     * This method draw number of element and return them on a list instance.
     * The deck is cleared after drawing the element.
     * @param number of element to draw
     * @return list of element drawn from the deck
     */
    public List<T> drawCards(int number) {
        if (deck.size() < number) number = deck.size();
        var list = new ArrayList<T>();
        for (int i = 0; i < number; i++) {
            list.add(draw());
        }
        return list;
    }

    /**
     * This method shuffle all the element of the deck
     */
    public void shuffle() {
        var list = deck.stream().collect(Collectors.toList());
        Collections.shuffle(list);
        deck.clear();
        deck.addAll(list);
    }

    /**
     * This method draw an element
     * @return element drawn
     */
    public T draw(){
        return deck.pop();
    }

    /**
     * This method clear all the element of the deck
     */
    public void clear() {
        deck.clear();
    }

    /**
     * This method return the size of the deck
     * @return size of the deck
     */
    public int size() {
        return deck.size();
    }
}
