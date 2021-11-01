package object;

import java.util.*;
import java.util.stream.Collectors;

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
        refreshDisplay();
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

    private void refreshDisplay(){
        var size = deck.size();
        var numberOfDigit = (size == 0)? 1: (int) Math.log10(size) + 1;
        var numberDisplay = getArrayNumber(numberOfDigit, size);
        var totalNumbersLength = numberOfDigit * Constants.LENGTH_NUMBER_DISPLAY + 1;
        var centerName = (totalNumbersLength - name.length()) / 2;
        centerName = (centerName > 0)? centerName : 0;
        var centerNumber = (name.length() - totalNumbersLength) / 2;
        centerNumber =(centerNumber > 0)? centerNumber: 0;
        System.out.println(centerNumber);
        var spacing = (totalNumbersLength > name.length())? totalNumbersLength : name.length();
        display = getString(spacing, centerName, numberDisplay, centerNumber);
    }

    private String[] getString(int spacing, int centerName, List<String[]> numberDisplay, int centerNumber){
        return new String[]{
                " " + "_".repeat(spacing + 2) +" ",
                "| " + " ".repeat(centerName) + name + " ".repeat((name.length() % 2 == 0) ? centerName + 1 : centerName) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 0) + " ".repeat(centerNumber) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 1) + " ".repeat(centerNumber) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 2) + " ".repeat(centerNumber) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 3) + " ".repeat(centerNumber) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 4) + " ".repeat(centerNumber) + " |",
                " " + "-".repeat(spacing + 2) + " "
        };
    }

    private List<String[]> getArrayNumber(int numberOfDigit, int size){
        var numberDisplay = new ArrayList<String[]>();
        for (var i = numberOfDigit - 1; i >= 0; i--){
            numberDisplay.add(NumbersDisplays.NUMBERS[size /(int) Math.pow(10, i)]);
            size %= Math.pow(10, i);
        }
        return numberDisplay;
    }

    private String getNumberDisplay(List<String[]> numberDisplay, int i) {
        return numberDisplay.stream().map(strings -> strings[i]).collect(Collectors.joining(" "));
    }

    @Override
    public String getDisplay(int i){
        if(i == 0){
            refreshDisplay();
        }
        return display[i];
    }

}
