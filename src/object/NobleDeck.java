package object;

import java.util.List;

public class NobleDeck extends Deck<Card>{

    public NobleDeck(String name){
        super(name);
    }

    @Override
    public int getNumberToDraw(int playerSize){
        return playerSize + 1;
    }

    @Override
    public List<Card> drawCards(int number){
        var list = super.drawCards(number);
        super.clear();
        return list;
    }
}
