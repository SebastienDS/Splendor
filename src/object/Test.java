package object;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        var numberDisplay = new ArrayList<Integer>();
        var number = 125;
        var numberOfDigit =(int) Math.log10(120) + 1;
        for (var i = numberOfDigit - 1; i >= 0; i--){
            numberDisplay.add(number /(int) Math.pow(10, i));
            number %= Math.pow(10, i);
        }
        System.out.println(numberDisplay);
    }
}
