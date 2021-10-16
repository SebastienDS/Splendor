package object;

public class Decks {

    private static final int NUMBER_BASIC_CARD = 8;

    public static Deck<Noble> BasicDeck(){
        var basicDeck = new Deck<Noble>();
        var tokenManager = new TokenManager();
        for(Token token: Token.values()){
            tokenManager.addToken(token, 3);
            addBasicCard(tokenManager, NUMBER_BASIC_CARD, basicDeck);
        }
        return basicDeck;
    }

    private static void addBasicCard(TokenManager tokenManager, int number, Deck<Noble> deck){
        for (int i = 0; i < number; i++) {
            deck.add(new Noble(tokenManager.tokenManager(), "", ""));
        }
    }

    public static Deck<Noble> NobleDeck(){
        var nobleDeck = new Deck<Noble>();
        var tokenManager = new TokenManager();
        return nobleDeck;
    }

    private static Noble createNoble(TokenManager tokenManager, Token[] tokens, int[] costs, String name, String image){
        for (int i = 0; i < tokens.length; i++) {
            tokenManager.addToken(tokens[i], costs[i]);
        }
        return new Noble(tokenManager.tokenManager(), name, image);
    }

    public static Deck<Development> FirstDevelopmentDeck(){
        var nobleDeck = new Deck<Development>();
        //nobleDeck.add(new Noble());
        return nobleDeck;
    }

    public static Deck<Development> SecondDevelopmentDeck(){
        var nobleDeck = new Deck<Development>();
        //nobleDeck.add(new Noble());
        return nobleDeck;
    }

    public static Deck<Development> ThirdDevelopmentDeck() {
        var nobleDeck = new Deck<Development>();
        //nobleDeck.add(new Noble());
        return nobleDeck;
    }
}
