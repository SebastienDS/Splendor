package object;

public class Utils {
    public static String space(int length) {
        if(length < 0) {
            return "";
        }
        return " ".repeat(length);
    }
}
