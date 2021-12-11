package object;

import java.util.*;
import java.util.stream.Collectors;

public class BasicDeck<T> implements Deck<T> {

    private final String name;
    private final Stack<T> deck;
    private String[] display;

    public BasicDeck(){
        this("");
    }

    public BasicDeck(String name) {
        Objects.requireNonNull(name);
        deck = new Stack<>();
        this.name = name;
        refreshDisplay();
    }

    @Override
    public void add(T t) {
        deck.add(t);
    }

    public List<T> drawCards(int number) {
        if(deck.size() < number) number = deck.size();
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

    private void refreshDisplay() {
        var size = deck.size();
        var numberOfDigit = (size == 0)? 1: (int) Math.log10(size) + 1;
        var numberDisplay = getArrayNumber(numberOfDigit, size);
        var totalNumbersLength = numberOfDigit * Constants.LENGTH_NUMBER_DISPLAY + (numberOfDigit - 1);
        var centerName = ((totalNumbersLength - name.length()) / 2);
        centerName = (centerName > 0)? centerName : 0;
        var centerName2 = getSecondCenter(totalNumbersLength, name.length(),centerName);
        var centerNumber = (name.length() - totalNumbersLength) / 2;
        centerNumber =(centerNumber > 0)? centerNumber: 0;
        var centerNumber2 = getSecondCenter(name.length(), totalNumbersLength, centerNumber);
        var spacing = (totalNumbersLength > name.length())? totalNumbersLength : name.length();
        display = getString(spacing, centerName, numberDisplay, centerNumber, centerName2, centerNumber2);
    }

    private int getSecondCenter(int firstLength, int secondLength, int valueNormalCenter) {
        if (firstLength > secondLength){
            return (firstLength % secondLength % 2 == 1) ? valueNormalCenter + 1: valueNormalCenter;
        }
        return 0;
    }

    private String[] getString(int spacing, int centerName, List<String[]> numberDisplay, int centerNumber,
                               int centerName2, int centerNumber2){
        return new String[]{
                " " + "_".repeat(spacing + 2) + " ",
                "| " + " ".repeat(centerName) + name + " ".repeat(centerName2) + " |",
                "|" + " ".repeat(spacing + 2) + "|",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 0) + " ".repeat(centerNumber2) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 1) + " ".repeat(centerNumber2) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 2) + " ".repeat(centerNumber2) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 3) + " ".repeat(centerNumber2) + " |",
                "| " + " ".repeat(centerNumber) + getNumberDisplay(numberDisplay, 4) + " ".repeat(centerNumber2) + " |",
                "|" + " ".repeat(spacing + 2) + "|",
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
    public String getDisplay(int i) {
        if(i == 0){
            refreshDisplay();
        }
        return display[i];
    }

    public void clear() {
        deck.clear();
    }

    public int getNumberToDraw(int playerSize) {
        return Constants.DRAW_NUMBER;
    }
}
