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
        var prestigeLength = String.valueOf(getPrestige()).length() + "Prestige: ".length();
        var bonusLength =  bonus.name().length() + "Bonus: ".length();
        var priceLength = "Price: ".length();
        var name = super.getName();
        var centerName = super.getCenterName();
        var string = new String[]{
                " "+ "_".repeat(Constants.MAX_LENGTH) +" ",
                "|" + space(centerName) + name + space((name.length() % 2 == 1)? centerName + 1 : centerName) + "|",
                "|Prestige: " + getPrestige() +space(Constants.MAX_LENGTH - prestigeLength) + "|",
                "|Bonus: " + bonus.name() + space(Constants.MAX_LENGTH - bonusLength) + "|",
                "|Price: " + space(Constants.MAX_LENGTH - priceLength) + "|",
                "|" + stringPrice(0) + space(Constants.MAX_LENGTH - stringPrice(0).length()) + "|",
                "|" + stringPrice(1) + space(Constants.MAX_LENGTH - stringPrice(1).length()) + "|",
                "|" + stringPrice(2) + space(Constants.MAX_LENGTH - stringPrice(2).length()) + "|",
                " " + "-".repeat(Constants.MAX_LENGTH) + " "
        };
        return string;
    }
}
