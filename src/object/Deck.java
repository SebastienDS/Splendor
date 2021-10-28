package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck<T> {

    private final Stack<T> deck;

    public Deck(){
        deck = new Stack<>();
    }

    public void add(T t){
        deck.add(t);
    }

    public List<T> drawMultiple(int number) {
        var list = new ArrayList<T>();
        for (int i = 0; i < number; i++) {
            list.add(draw());
        }
        return list;
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public T draw(){
        return deck.pop();
    }

    public int size() {
        return deck.size();
    }
}
