package object;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static object.Utils.space;

public class Noble implements Card {

    public static final String TYPE = "Noble";

    private final Card card;

    private final String[] display;
    private final List<String> price;

    public Noble(Map<Token, Integer> cost, String name, String image, int prestige) {
        card = new BasicCard(cost, name, image, prestige);

        price = cost.keySet().stream().map(token -> token.name() + " : " + cost.get(token)).toList();
        display = stringDisplay();
    }

    @Override
    public String getDisplay(int i) {
        return display[i];
    }

    @Override
    public String getName() {
        return card.getName();
    }

    @Override
    public int getCost(Token token) {
        return card.getCost(token);
    }

    @Override
    public Set<Token> getTokens() {
        return card.getTokens();
    }

    @Override
    public int getPrestige() {
        return card.getPrestige();
    }

    @Override
    public Token getBonus() {
        return card.getBonus();
    }

    private String[] stringDisplay() {
        var prestigeLength = String.valueOf(getPrestige()).length() + "Prestige: ".length();
        var priceLength = "Price: ".length();
        var centerName = getCenterName();
        var name = getName();
        var string = new String[] {
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

    private String stringPrice(int i){
        return (card.getTokens().size() >  i)? price.get(i) : "";
    }

    private int getCenterName(){
        return (Constants.MAX_LENGTH - card.getName().length()) / 2;
    }
}
