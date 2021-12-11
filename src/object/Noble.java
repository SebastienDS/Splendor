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

    @Override
    public Token getBonus() {
        return null;
    }

    private String[] stringDisplay() {
        var prestigeLength = String.valueOf(getPrestige()).length() + "Prestige: ".length();
        var priceLength = "Price: ".length();
        var centerName = super.getCenterName();
        var name = super.getName();
        var string = new String[]{
                " "+ "_".repeat(Constants.MAX_LENGTH) +" ",
                "|" + space(centerName) + name + space((name.length() % 2 == 1)? centerName + 1 : centerName) + "|",
                "|Prestige: " + getPrestige() +space(Constants.MAX_LENGTH - prestigeLength) + "|",
                "|" + space(Constants.MAX_LENGTH) + "|",
                "|Price: " + space(Constants.MAX_LENGTH - priceLength) + "|",
                "|" + stringPrice(0) + space(Constants.MAX_LENGTH - stringPrice(0).length()) + "|",
                "|" + stringPrice(1) + space(Constants.MAX_LENGTH - stringPrice(1).length()) + "|",
                "|" + stringPrice(2) + space(Constants.MAX_LENGTH - stringPrice(2).length()) + "|",
                "|" + stringPrice(3) + space(Constants.MAX_LENGTH - stringPrice(3).length()) + "|",
                " " + "-".repeat(Constants.MAX_LENGTH) + " "
        };
        return string;
    }
}
