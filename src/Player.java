import java.util.ArrayList;
import java.util.List;

public class Player {

    private final Wallet wallet;
    private final List<Development> cardBuy;

    public Player() {
        wallet = new Wallet();
        cardBuy = new ArrayList<>();
    }
}
