package object;

public interface Card extends Displayable{
    int getCost(Token token);
    String getDisplay(int i);
    int getPrestige();
}
