package object;

public class NumbersDisplays {

    public static String[][] NUMBERS;

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

    private static String[] initZero() {
        return new String[]{
                "000000",
                "0    0",
                "0    0",
                "0    0",
                "000000"
        };
    }

    private static String[] initOne(){
        return new String[]{
                "1111  ",
                "  11  ",
                "  11  ",
                "  11  ",
                "111111"
        };
    }

    private static String[] initTwo() {
        return new String[]{
                "222222",
                "    22",
                "222222",
                "22    ",
                "222222"
        };
    }

    private static String[] initThree() {
        return new String[]{
                "333333",
                "    33",
                "333333",
                "    33",
                "333333"
        };
    }

    private static String[] initFour() {
        return new String[]{
                "44  44",
                "44  44",
                "444444",
                "    44",
                "    44"
        };
    }

    private static String[] initFive() {
        return new String[]{
                "555555",
                "55    ",
                "555555",
                "    55",
                "555555"
        };
    }

    private static String[] initSix() {
        return new String[]{
                " 6666 ",
                "66    ",
                "66666 ",
                "66  66",
                " 6666 "
        };
    }

    private static String[] initSeven() {
        return new String[]{
                "777777",
                "   77 ",
                "  77  ",
                " 77   ",
                "77    "
        };
    }

    private static String[] initEight() {
        return new String[]{
                " 8888 ",
                "88  88",
                " 8888 ",
                "88  88",
                " 8888"
        };
    }

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
