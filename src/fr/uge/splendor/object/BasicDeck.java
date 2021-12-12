package fr.uge.splendor.object;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents the deck of the game
 * @param <T> element of the deck
 */
public class BasicDeck<T> implements Deck<T> {

    /**
     * Name of the deck
     */
    private final String name;

    /**
     * Stack representing the deck
     */
    private final Stack<T> deck;

    /**
     * Table representing the string to display
     */
    private String[] display;

    /**
     * Create an instance of BasicDeck with an empty string as a name
     */
    public BasicDeck(){
        this("");
    }

    /**
     * Create an instance of BasicDeck with the specified name
     * @param name name given to the deck
     */
    public BasicDeck(String name) {
        Objects.requireNonNull(name);
        deck = new Stack<>();
        this.name = name;
        refreshDisplay();
    }

    /**
     * This method add the specified element to the deck
     * @param t element to add
     * @Override add in interface Deck
     */
    @Override
    public void add(T t) {
        deck.add(t);
    }

    /**
     * This method draw number of element and return them on a list instance.
     * The deck is cleared after drawing the element.
     * @param number of element to draw
     * @return list of element drawn from the deck
     * @Override drawCards of interface Deck
     */
    @Override
    public List<T> drawCards(int number) {
        if(deck.size() < number) number = deck.size();
        var list = new ArrayList<T>();
        for (int i = 0; i < number; i++) {
            list.add(draw());
        }
        return list;
    }

    /**
     * This method shuffle all the element of the deck
     * @Override shuffle of interface Deck
     */
    @Override
    public void shuffle(){
        Collections.shuffle(deck);
    }

    /**
     * This method draw an element
     * @return element drawn
     * @Override draw of interface deck
     */
    @Override
    public T draw(){
        return deck.pop();
    }

    /**
     * This method return the name of the deck
     * @return name of the deck
     */
    public String getName() {
        return name;
    }

    /**
     * This method refresh the value saved of the display
     */
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

    /**
     * This method return the second value needed to center the text. This value differ when the text is pair or impair.
     * @param firstLength total length
     * @param secondLength length to center
     * @param valueNormalCenter value to center it without caring about pair or impair text
     * @return second value needed to center the text
     */
    private int getSecondCenter(int firstLength, int secondLength, int valueNormalCenter) {
        if (firstLength > secondLength){
            return (firstLength % secondLength % 2 == 1) ? valueNormalCenter + 1: valueNormalCenter;
        }
        return 0;
    }

    /**
     * This method create a table of string corresponding of the display of the deck
     * @param spacing space between the start of the card and the end
     * @param centerName length of space to put at the left to center the name
     * @param numberDisplay List of table of string corresponding to the display of the number of element in the deck
     * @param centerNumber length of space to put at the left to center the number
     * @param centerName2 length of space to put at the right to center the name
     * @param centerNumber2 length of space to put at the right to center the number
     * @return the table created
     */
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

    /**
     * This method return the list of table of string corresponding to the display of number
     * @param numberOfDigit number of digits in the size
     * @param size size of deck
     * @return list of table of string corresponding to the display of number
     */
    private List<String[]> getArrayNumber(int numberOfDigit, int size){
        var numberDisplay = new ArrayList<String[]>();
        for (var i = numberOfDigit - 1; i >= 0; i--){
            numberDisplay.add(NumbersDisplays.NUMBERS[size /(int) Math.pow(10, i)]);
            size %= Math.pow(10, i);
        }
        return numberDisplay;
    }

    /**
     * This method return the sum of all the string of the i-th element of each table of number display and separate them by a space
     * @param numberDisplay list of table of string corresponding to display of the size of deck
     * @param i th line
     * @return joining of all the string of the i-th element of each table of numberDisplay with a space delimiter
     */
    private String getNumberDisplay(List<String[]> numberDisplay, int i) {
        return numberDisplay.stream().map(strings -> strings[i]).collect(Collectors.joining(" "));
    }

    /**
     * This method return the string of the display of the deck for the i-th line
     * @param i th line
     * @return string of the display of the deck for the i-th line
     * @Override getDisplay of interface displayable
     */
    @Override
    public String getDisplay(int i) {
        if(i == 0){
            refreshDisplay();
        }
        return display[i];
    }

    /**
     * This method clear all the element of the deck
     * @Override clear of interface Deck
     */
    @Override
    public void clear() {
        deck.clear();
    }

    /**
     * This method return the number of element that will be drawn at the start
     * @param playerSize number of player
     * @return number of card that will be drawn at the start
     * @Override getNumberToDraw of interface deck
     */
    @Override
    public int getNumberToDraw(int playerSize) {
        return Constants.DRAW_NUMBER;
    }
}
