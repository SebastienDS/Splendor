package object;

import java.util.Map;

import static object.Utils.space;

public class Development extends AbstractCard {

    public static final String TYPE = "Development";

    private final Token bonus;
    private final String[] display;

    public Development(Map<Token, Integer> cost, String name, String image, int prestige, Token bonus) {
        super(cost, name, image, prestige);
        this.bonus = bonus;
        display = stringDisplay();
    }

    @Override
    public String getDisplay(int i) {
        if(i > display.length){
            throw new IllegalArgumentException("i(" + i +") is higher than length of display(" + display.length + ")");
        }
        return display[i];
    }

    private String[] stringDisplay() {
        var prestigeLength = String.valueOf(getPrestige()).length();
        var string = new String[]{
                "_______________",
                "prestige : " + getPrestige() +space(Constants.MAX_LENGTH - prestigeLength) + "|",
                "Bonus: " + bonus.name() + space(Constants.MAX_LENGTH - bonus.name().length()) + "|",
                "Price: ",
                "|" + stringPrice(0) + space(Constants.MAX_LENGTH - stringPrice(0).length()) + "|",
                "|" + stringPrice(1) + space(Constants.MAX_LENGTH - stringPrice(1).length()) + "|",
                "|" + stringPrice(2) + space(Constants.MAX_LENGTH - stringPrice(2).length()) + "|",
                "----------------"
        };
        return string;
    }
}
