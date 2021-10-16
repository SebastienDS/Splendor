import java.util.Collections;
import java.util.Stack;

public class Deck<T> {

    Stack<T> deck;

    public Deck(){
        deck = new Stack<>();
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }
}
