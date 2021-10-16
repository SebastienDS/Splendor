package ModeleVueController;

import object.Card;
import object.Deck;
import object.DeckName;
import object.Player;

import java.util.List;
import java.util.Map;

public class Model {

    private final List<Player> players;
    private int playerPlaying;
    private final Map<DeckName, Deck> decks;
    private final Map<String, List<Card>> grounds;

    public Model(List<Player> players, Map<DeckName, Deck> decks, Map<String, List<Card>> grounds) {
        this.players = players;
        playerPlaying = 0;
        this.decks = decks;
        this.grounds = grounds;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<DeckName, Deck> getDecks() {
        return decks;
    }

    public Map<String, List<Card>> getGrounds() {
        return grounds;
    }
}
