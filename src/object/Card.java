package object;

import java.util.Set;

public interface Card extends Displayable{
    int getCost(Token token);
    Set<Token> getTokens();
    String getDisplay(int i);
    int getPrestige();
    Token getBonus();
}
