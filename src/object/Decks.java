package object;

public class Decks {

    public static object.Deck<Card> BasicDeck(){
        object.Deck<Card> basicDeck = new object.Deck<>();
        //nobleDeck.add(new Noble());
        return basicDeck;
    }

    public static Deck<Noble> NobleDeck(){
        Deck<Noble> nobleDeck = new Deck<>();
        //nobleDeck.add(new object.Noble());
        return nobleDeck;
    }

    public static Deck<Development> FirstDevelopmentDeck(){
        Deck<Development> nobleDeck = new Deck<>();
        //nobleDeck.add(new object.Noble());
        return nobleDeck;
    }

    public static Deck<Development> SecondDevelopmentDeck(){
        Deck<Development> nobleDeck = new Deck<>();
        //nobleDeck.add(new object.Noble());
        return nobleDeck;
    }

    public static Deck<Development> ThirdDevelopmentDeck(){
        Deck<Development> nobleDeck = new Deck<>();
        //nobleDeck.add(new object.Noble());
        return nobleDeck;
    }
}
