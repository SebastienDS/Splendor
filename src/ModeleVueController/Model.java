package ModeleVueController;

import object.*;

import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private int gameMode = 1;
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

    public void setGameMode(int gameMode) {
        if (gameMode <= 0) throw new IllegalArgumentException("GameMode must be >= 0");
        this.gameMode = gameMode;
    }

    public int getGameMode() {
        return gameMode;
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

    public Map<DeckName, List<Card>> getGroundsWithoutNoble(){
        return grounds.keySet().stream().filter(deckName -> deckName != DeckName.NOBLE_DECK).collect(Collectors.toMap(
                deckName -> deckName,
                deckName -> grounds.get(deckName),
                (a, b) -> {throw new IllegalStateException("duplicated key");},
                LinkedHashMap::new
        ));
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
        for (var deckName: decks.keySet()) {
            var list = new ArrayList<Card>();
            var deck = decks.get(deckName);
            list.addAll(deck.drawCards(deck.getNumberToDraw(players.size())));
            grounds.put(deckName, list);
        }
    }

    public boolean reservePossible(){
        return gameMode >= 2;
    }

    public void addTokenUsed(Map<Token, Integer> tokens) {
        for (var token: tokens.keySet()){
            gameTokens.addToken(token, tokens.get(token));
        }
    }
}
