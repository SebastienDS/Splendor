package object;

import java.util.Map;
import java.util.Objects;

public class Noble extends AbstractCard {

    private final String name;
    private final String[] display;

    public Noble(Map<Token, Integer> cost, String name, String image, int prestige) {
        super(cost, image, prestige);
        this.name = Objects.requireNonNull(name);
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

    private String space(int maxLength) {
        return " ".repeat(maxLength);
    }
}
