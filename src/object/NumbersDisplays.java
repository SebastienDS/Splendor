package object;

/**
 * Represents all drawing of digit in console
 */
public class NumbersDisplays {

    /**
     * This field save all the drawing of digit
     */
    public static String[][] NUMBERS;

    /**
     * This method load all the drawing of the digit and save them in the field NUMBERS if the drawing weren't loaded
     * before
     */
    public static void loadNumbers(){
        if(NUMBERS != null){
            return;
        }
        NUMBERS = new String[][]{
                initZero(),
                initOne(),
                initTwo(),
                initThree(),
                initFour(),
                initFive(),
                initSix(),
                initSeven(),
                initEight(),
                initNine()
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 0
     * @return a table of string representing the drawing of the digit 0
     */
    private static String[] initZero() {
        return new String[]{
                "000000",
                "0    0",
                "0    0",
                "0    0",
                "000000"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 1
     * @return a table of string representing the drawing of the digit 1
     */
    private static String[] initOne(){
        return new String[]{
                "1111  ",
                "  11  ",
                "  11  ",
                "  11  ",
                "111111"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 2
     * @return a table of string representing the drawing of the digit 2
     */
    private static String[] initTwo() {
        return new String[]{
                "222222",
                "    22",
                "222222",
                "22    ",
                "222222"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 3
     * @return a table of string representing the drawing of the digit 3
     */
    private static String[] initThree() {
        return new String[]{
                "333333",
                "    33",
                "333333",
                "    33",
                "333333"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 4
     * @return a table of string representing the drawing of the digit 4
     */
    private static String[] initFour() {
        return new String[]{
                "44  44",
                "44  44",
                "444444",
                "    44",
                "    44"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 5
     * @return a table of string representing the drawing of the digit 5
     */
    private static String[] initFive() {
        return new String[]{
                "555555",
                "55    ",
                "555555",
                "    55",
                "555555"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 6
     * @return a table of string representing the drawing of the digit 6
     */
    private static String[] initSix() {
        return new String[]{
                " 6666 ",
                "66    ",
                "66666 ",
                "66  66",
                " 6666 "
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 7
     * @return a table of string representing the drawing of the digit 7
     */
    private static String[] initSeven() {
        return new String[]{
                "777777",
                "   77 ",
                "  77  ",
                " 77   ",
                "77    "
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 8
     * @return a table of string representing the drawing of the digit 8
     */
    private static String[] initEight() {
        return new String[]{
                " 8888 ",
                "88  88",
                " 8888 ",
                "88  88",
                " 8888"
        };
    }

    /**
     * This method return a table of string representing the drawing of the digit 9
     * @return a table of string representing the drawing of the digit 9
     */
    private static String[] initNine() {
        return new String[]{
                " 9999 ",
                "99  99",
                " 99999",
                "    99",
                " 9999 "
        };
    }
}
