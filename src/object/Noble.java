package object;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static object.Utils.space;

public class Noble extends AbstractCard {

    public static final String TYPE = "Noble";

    private final String[] display;

    public Noble(Map<Token, Integer> cost, String name, String image, int prestige) {
        super(cost, name, image, prestige);
        display = stringDisplay();
    }

    @Override
    public String getDisplay(int i) {
        return display[i];
    }

    private String[] stringDisplay() {
        var prestigeLength = String.valueOf(getPrestige()).length() + "prestige : ".length();
        var string = new String[]{
                " "+ "_".repeat(Constants.MAX_LENGTH) +" ",
                "|prestige : " + getPrestige() +space(Constants.MAX_LENGTH - prestigeLength) + "|",
                "|" + space(Constants.MAX_LENGTH) + "|",
                "|Price: " + space(Constants.MAX_LENGTH - "Price: ".length()) + "|",
                "|" + stringPrice(0) + space(Constants.MAX_LENGTH - stringPrice(0).length()) + "|",
                "|" + stringPrice(1) + space(Constants.MAX_LENGTH - stringPrice(1).length()) + "|",
                "|" + stringPrice(2) + space(Constants.MAX_LENGTH - stringPrice(2).length()) + "|",
                " " + "-".repeat(Constants.MAX_LENGTH) + " "
        };
        return string;
    }
}
