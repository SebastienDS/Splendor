package object;

import java.util.Set;

public interface Card extends Displayable {
    String getName();
    int getCost(Token token);
    Set<Token> getTokens();
    int getPrestige();

    default Token getBonus() {
        return null;
    }
}
