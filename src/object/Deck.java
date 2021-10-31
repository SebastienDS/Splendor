package object;

import java.util.*;

public class Deck<T> {

    private final String name;
    private final Stack<T> deck;

    public Deck(){
        this("");
    }

    public Deck(String name){
        Objects.requireNonNull(name);
        deck = new Stack<>();
        this.name = name;
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

    public String getName() {
        return name;
    }

}
