package object;

import java.util.*;

import static object.Utils.space;

public class Deck<T> implements Displayable {

    private final String name;
    private final Stack<T> deck;
    private String[] display;

    public Deck(){
        this("");
    }

    public Deck(String name){
        Objects.requireNonNull(name);
        deck = new Stack<>();
        this.name = name;
        display = refreshDisplay();
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

    private String[] refreshDisplay(){
        var size = deck.size();
        var numberOfDigit = (size == 0)? 1: (int) Math.log10(size) + 1;
        var numberDisplay = new ArrayList<String[]>();
        for (var i = 0; i < numberOfDigit; i++){
            numberDisplay.add(NumbersDisplays.NUMBERS[size % (10 * (numberOfDigit - i))]);
        }
        var string = new String[]{
                " " + "_".repeat(numberOfDigit * Constants.LENGTH_NUMBER_DISPLAY) +" ",
                "|" +  "|",
                "|" + "|",
                "|" + "|",
                "|" + "|",
                "|" + "|",
                "|" + "|",
                " " + "-".repeat(numberOfDigit * Constants.LENGTH_NUMBER_DISPLAY) + " "
        };
        return string;
    }

    @Override
    public String getDisplay(int i){
        if(i == 0){
            refreshDisplay();
        }
        return display[i];
    }

}
