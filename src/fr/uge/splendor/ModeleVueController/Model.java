package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents all data of the game
 */
public class Model {

    /**
     * Game mode of the game
     */
    private int gameMode = 1;

    /**
     * List of all players of the game
     */
    private final List<Player> players;

    /**
     * Integer corresponding at the current player playing on the list of players
     */
    private int playerPlaying;

    /**
     * True if current round is the last
     */
    private boolean lastRound = false;

    /**
     * Represents the card that will be drawn for the grounds
     */
    private final Map<DeckName, Deck<Card>> decks;

    /**
     * Represents the cards visible from the deck
     */
    private final Map<DeckName, List<Card>> grounds;

    /**
     * represents all the tokens that player can take
     */
    private final TokenManager gameTokens;

    /**
     * Create an instance of model
     * @param players list of all players
     * @param decks decks of the games
     * @param grounds grounds of the games
     */
    public Model(List<Player> players, Map<DeckName, Deck<Card>> decks, Map<DeckName, List<Card>> grounds) {
        this.players = Objects.requireNonNull(players);
        this.decks = Objects.requireNonNull(decks);
        this.grounds = Objects.requireNonNull(grounds);
        gameTokens = new TokenManager();
    }

    /**
     * This method set the game mode
     * @param gameMode new game mode
     */
    public void setGameMode(int gameMode) {
        if (gameMode <= 0) throw new IllegalArgumentException("GameMode must be >= 0");
        this.gameMode = gameMode;
    }

    /**
     * This method return the game mode
     * @return game mode
     */
    public int getGameMode() {
        return gameMode;
    }

    /**
     * This method return the list of all the players
     * @return list of all the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * This method return the decks. A map containing the deckName as a key and a deck as a value.
     * @return return map containing all the deck with deck name as key
     */
    public Map<DeckName, Deck<Card>> getDecks() {
        return decks;
    }

    /**
     * This method return the grounds. A map containing the deckName as a key and a list of card as a value
     * @return grounds
     */
    public Map<DeckName, List<Card>> getGrounds() {
        return grounds;
    }

    /**
     * This method return the map of grounds without the grounds of noble
     * @return map of grounds without the grounds of noble
     */
    public Map<DeckName, List<Card>> getGroundsWithoutNoble(){
        return grounds.keySet().stream().filter(deckName -> deckName != DeckName.NOBLE_DECK).collect(Collectors.toMap(
                deckName -> deckName,
                deckName -> grounds.get(deckName),
                (a, b) -> {throw new IllegalStateException("duplicated key");},
                LinkedHashMap::new
        ));
    }

    /**
     * This method initialise value to begin the game
     */
    public void startGame() {
        shuffleDecks();
        initGrounds();
        initGameTokens(players.size());
    }

    /**
     * This method shuffles all the decks of the games
     */
    private void shuffleDecks() {
        decks.values().forEach(deck -> deck.shuffle());
    }

    /**
     * This method see verify if current round is the last and change the player that play to the next one
     */
    public void endTurn() {
        lastRound = lastRound || players.get(playerPlaying).getPrestige() >= 15;
        playerPlaying = (playerPlaying + 1) % players.size();
    }

    /**
     * This method return true if the current round is the last
     * @return ture if the current round is the last
     */
    public boolean getLastRound(){
        return lastRound;
    }

    /**
     * This method return the player currently playing
     * @return player playing
     */
    public Player getPlayerPlaying() {
        return players.get(playerPlaying);
    }

    /**
     * This method return true if the current player is the first player
     * @return true if the current player is the first player
     */
    public boolean startNewRound() {
        return playerPlaying == 0;
    }

    /**
     * This method return the game tokens
     * @return gameTokens
     */
    public TokenManager getGameTokens() {
        return gameTokens;
    }

    /**
     * This method take token from game token and add them to the player wallet
     * @param chooseToken specified token
     * @param number number of token to take
     */
    public void takeToken(Token chooseToken, int number) {
        gameTokens.addToken(chooseToken, -number);
        players.get(playerPlaying).addToken(chooseToken, number);
    }

    /**
     * This methode initialise the game tokens in function of the number of player
     * @param size size of player list
     */
    private void initGameTokens(int size) {
        var numberOfTokens = size + 2 + size / 4;
        for (var token: Token.cardValues()) {
            gameTokens.addToken(token, numberOfTokens);
        }
        gameTokens.addToken(Token.GOLD, 5);
    }

    /**
     * This method initialise all grounds by drawing card of deck and putting them on grounds
     */
    private void initGrounds() {
        for (var deckName: decks.keySet()) {
            var list = new ArrayList<Card>();
            var deck = decks.get(deckName);
            list.addAll(deck.drawCards(deck.getNumberToDraw(players.size())));
            grounds.put(deckName, list);
        }
    }

    /**
     * This method return true if reserve is available for the gameMode chosen
     * @return true if reverse is available
     */
    public boolean reservePossible(){
        return gameMode >= 2;
    }

    /**
     * This method add token of tokens to the game tokens
     * @param tokens map containing token as key and integer as value
     */
    public void addTokenUsed(Map<Token, Integer> tokens) {
        for (var token: tokens.keySet()){
            gameTokens.addToken(token, tokens.get(token));
        }
    }

    /**
     * This method return the winning player
     * @return Player winner
     */
    public Player getWinner() {
        var maxPrestigePlayer = players.stream()
                .filter(player ->
                        player.getPrestige() >= players.stream()
                                                .mapToInt(player1 -> player1.getPrestige()).max().getAsInt()).toList();
        if(maxPrestigePlayer.size() == 1){
            return maxPrestigePlayer.get(0);
        }
        return maxPrestigePlayer.stream().min(Comparator.comparingInt(Player::getCardPurchased)).get();
    }
}
