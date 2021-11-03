package ModeleVueController;

import object.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Model {

    private final List<Player> players;
    private int playerPlaying;
    private boolean lastRound = false;
    private final Map<DeckName, Deck<Card>> decks;
    private final Map<DeckName, List<Card>> grounds;
    private final TokenManager gameTokens;

    public Model(List<Player> players, Map<DeckName, Deck<Card>> decks, Map<DeckName, List<Card>> grounds) {
        this.players = Objects.requireNonNull(players);
        this.decks = Objects.requireNonNull(decks);
        this.grounds = Objects.requireNonNull(grounds);
        gameTokens = new TokenManager();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<DeckName, Deck<Card>> getDecks() {
        return decks;
    }

    public Map<DeckName, List<Card>> getGrounds() {
        return grounds;
    }

    public void startGame() {
        shuffleDecks();
        initGrounds();
        initGameTokens(players.size());
    }

    private void shuffleDecks() {
        decks.values().forEach(deck -> deck.shuffle());
    }

    public void endTurn() {
        lastRound = players.get(playerPlaying).getPrestige() >= 15;
        playerPlaying = (playerPlaying + 1) % players.size();
    }

    public boolean getLastRound(){
        return lastRound;
    }

    public Player getPlayerPlaying() {
        return players.get(playerPlaying);
    }

    public boolean startNewRound() {
        return playerPlaying == 0;
    }

    public TokenManager getGameTokens() {
        return gameTokens;
    }

    public void takeToken(Token chooseToken, int number) {
        gameTokens.addToken(chooseToken, -number);
        players.get(playerPlaying).addToken(chooseToken, number);
    }

    private void initGameTokens(int size) {
        var numberOfTokens = size + 2 + size / 4;
        for (var token: Token.cardValues()) {
            gameTokens.addToken(token, numberOfTokens);
        }
        gameTokens.addToken(Token.GOLD, 5);
    }

    private void initGrounds() {
        //if(decks.containsKey(DeckName.NOBLE_DECK)) {
          //  System.out.println(decks);
            //initNobleGround(decks.get(DeckName.NOBLE_DECK));
        //}
        //decks.keySet().stream().filter(deckName -> deckName != DeckName.NOBLE_DECK).forEach(deckName -> {
        for (var deckName: decks.keySet()) {
            var list = new ArrayList<Card>();
            list.addAll(decks.get(deckName).drawCards(4));
            grounds.put(deckName, list);
        }
    }

    /*private void initNobleGround(Deck<Card> noble) {
        var list = new ArrayList<Card>();
        list.addAll(noble.drawMultiple(players.size() + 1));
        grounds.put(deckName, list);
        grounds.put(DeckName.NOBLE_DECK).addAll();
        noble.clear();
    }*/
}
