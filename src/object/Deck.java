package object;

import java.util.Collections;
import java.util.Stack;

public class Deck<T> {

    private final Stack<T> deck;

    public Deck(){
        deck = new Stack<>();
    }

    public void add(T t){
        deck.add(t);
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public T draw(){
        return deck.pop();
    }
}
