package object;

import java.util.List;

public interface Deck<T> extends Displayable {
    void add(T t);
    List<T> drawCards(int number);
    void clear();
    void shuffle();
    int getNumberToDraw(int size);
    T draw();
}
