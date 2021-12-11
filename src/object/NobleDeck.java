package object;

import java.util.List;
import java.util.Objects;

public class NobleDeck implements Deck<Card> {

    private final Deck<Card> deck;

    public NobleDeck(String name) {
        deck = new BasicDeck<>(name);
    }

    @Override
    public int getNumberToDraw(int playerSize) {
        return playerSize + 1;
    }

    @Override
    public Card draw() {
        return deck.draw();
    }

    public List<Card> drawCards(int number) {
        var list = deck.drawCards(number);
        deck.clear();
        return list;
    }

    @Override
    public void shuffle() {
        deck.shuffle();
    }

    @Override
    public void clear() {
        deck.clear();
    }

    @Override
    public String getDisplay(int i) {
        return "";
    }

    @Override
    public void add(Card card) {
        Objects.requireNonNull(card);
        deck.add(card);
    }
}
