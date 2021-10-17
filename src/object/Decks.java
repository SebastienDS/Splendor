package object;

public class Decks {

    private static final int NUMBER_BASIC_CARD = 8;

    public static Deck<Noble> basicDeck(){
        var basicDeck = new Deck<Noble>();
        var tokenManager = new TokenManager();
        for(Token token: Token.values()){
            if(token.equals(Token.GOLD)){
                continue;
            }
            tokenManager.addToken(token, 3);
            addBasicCard(tokenManager, NUMBER_BASIC_CARD, basicDeck);
            tokenManager.clear();
        }
        return basicDeck;
    }

    private static void addBasicCard(TokenManager tokenManager, int number, Deck<Noble> deck){
        for (int i = 0; i < number; i++) {
            deck.add(new Noble(tokenManager.tokenNotEmpty(), "", "", 1));
        }
    }

    public static Deck<Noble> nobleDeck(){
        var nobleDeck = new Deck<Noble>();
        var tokenManager = new TokenManager();
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.EMERALD, Token.SAPPHIRE, Token.RUBY},
                new int[]{3, 3, 3},
                "Catherine de' Medici",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.ONYX, Token.SAPPHIRE, Token.DIAMOND},
                new int[]{3, 3, 3},
                "Elisabeth Of Austria",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.ONYX, Token.DIAMOND},
                new int[]{4, 4},
                "Isabella I Of Castille",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.SAPPHIRE, Token.DIAMOND},
                new int[]{4, 4},
                "Niccolò Machiavelli",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.EMERALD, Token.SAPPHIRE},
                new int[]{4, 4},
                "Suleiman The Magnificent",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.EMERALD, Token.SAPPHIRE, Token.DIAMOND},
                new int[]{3, 3, 3},
                "Anne Of Brittany",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.ONYX, Token.RUBY, Token.DIAMOND},
                new int[]{3, 3, 3},
                "Charles V",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.ONYX, Token.RUBY, Token.EMERALD},
                new int[]{3, 3, 3},
                "Francis I Of France",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.ONYX, Token.RUBY},
                new int[]{4, 4},
                "Henry VII",
                "",
                3)
        );
        nobleDeck.add(createNoble(
                tokenManager,
                new Token[]{Token.RUBY, Token.EMERALD},
                new int[]{4, 4},
                "Mary Stuart",
                "",
                3)
        );
        return nobleDeck;
    }

    private static Noble createNoble(TokenManager tokenManager, Token[] tokens, int[] costs, String name,
                                     String image, int prestige){
        for (int i = 0; i < tokens.length; i++) {
            tokenManager.addToken(tokens[i], costs[i]);
            tokenManager.clear();
        }
        return new Noble(tokenManager.tokenNotEmpty(), name, image, prestige);
    }

    public static Deck<Development> firstDevelopmentDeck(){
        var nobleDeck = new Deck<Development>();
        //nobleDeck.add(new Noble());
        return nobleDeck;
    }

    public static Deck<Development> secondDevelopmentDeck(){
        var nobleDeck = new Deck<Development>();
        //nobleDeck.add(new Noble());
        return nobleDeck;
    }

    public static Deck<Development> thirdDevelopmentDeck() {
        var nobleDeck = new Deck<Development>();
        //nobleDeck.add(new Noble());
        return nobleDeck;
    }
}
