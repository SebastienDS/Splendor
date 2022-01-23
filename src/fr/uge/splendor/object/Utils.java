package fr.uge.splendor.object;

/**
 * Represents all statics functions used in the project
 */
public class Utils {
    /**
     * This method return a string of only space with the specified length or an empty string if length is negative
     * @param length of string
     * @return string of only space with the specified length or an empty string if length is negative
     */
    public static String space(int length) {
        if(length < 0) {
            return "";
        }
        return " ".repeat(length);
    }
}
