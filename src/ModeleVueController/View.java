package ModeleVueController;

import object.*;

import java.util.List;
import java.util.Map;

public class View {
    public static void printStartingMenu() {
        var string = "Choississez un menu:\n" +
                "0: Menu des Joueurs\n" +
                "1: Choix du jeu\n" +
                "2: Lancer la partie";
        System.out.println(string);
    }

    public static void printGameModeMenu() {
        var string = "Choississez le mode de jeu:\n" +
                "1: Mode de jeu partie 1\n" +
                "2: Mode de jeu partie 2 / 3";
        System.out.println(string);
    }

    public static void printPlayerMenu(Model gameData) {
        var sb = new StringBuilder();
        sb.append("Joueur Actuelle:\n");
        gameData.getPlayers().forEach(player -> sb.append(player.getName()).append('\n'));

        sb.append("Que voulez vous faire?\n")
            .append("0: Modifier un nom\n")
            .append("1: Ajouter un joueur\n")
            .append("2: Supprimer un joueur\n")
            .append("3: Quitter le menu joueur");

        System.out.println(sb);
    }

    public static void printTooMuchPlayer() {
        System.out.println("Le nombre maximum de joueur est de 4 !");
    }

    public static void printNotEnoughPlayer() {
        System.out.println("Le nombre minimum de joueur est de 2 !");
    }

    public static void printChoiceDoNotExist(int n) {
        System.out.println("Le choix: \"" + n + "\" n'existe pas");
    }

    public static void printNeedNumber() {
        System.out.println("Vous devez écrire un nombre !");
    }

    public static void printChoosePlayer(List<Player> players) {
        var text = new StringBuilder();
        text.append("Qui choisissez vous?");
        for (int i = 0; i < players.size(); i++) {
            text.append('\n').append(i).append(": ").append(players.get(i).getName());
        }
        System.out.println(text.toString());
    }

    public static void printChooseName() {
        System.out.println("Choisissez un nom :");
    }

    public static void printPlayerPlaying(Player player) {
        System.out.println("C'est au tour du joueur: \"" + player.getName() + "\" de joué! ");
    }

    public static void printPlayerResource(Player playerPlaying) {
        System.out.println(playerPlaying.getName() + ' ' + playerPlaying.getWallet());
    }

    public static void printFirstChoicePlayer() {
        var text = new StringBuilder();
        text.append("Que voulez vous faire ?\n")
            .append("1: Prendre 2 jetons de la même couleur\n")
            .append("2: Prendre 3 jetons de couleurs différentes\n")
            .append("3: Réserver 1 carte développement et prendre 1 or\n")
            .append("4: Acheter 1 carte développement face visible au centre de la table ou préalablement réservée.");
        System.out.println(text);
    }

    public static void printTokens(Model gameData) {
        var text = new StringBuilder();
        text.append("Quel jeton ?");
        var gameTokens = gameData.getGameTokens();
        var iteration = 1;
        for (var token: Token.cardValues()) {
            text.append('\n').append(iteration).append(": ").append(token).append("(quantity:").append(gameTokens.get(token)).append(")");
            iteration++;
        }
        System.out.println(text);
    }

    public static void printMenuChooseToken() {
        var text = new StringBuilder();
        text.append("Que voulez vous faire?\n")
            .append("1: Choisir un jeton\n")
            .append("2: Retirer un jeton choisis\n")
            .append("3: Confirmer le choix");
        System.out.println(text);
    }

    public static void printNotEnoughToken() {
        System.out.println("Le nombre de jeton choisis restant est insuffisant !");
    }

    public static void printTooMuchToken() {
        System.out.println("Vous avez déjà séléctionner 3 jetons !");
    }

    public static void printTokenAlreadyChosen(String tokenName) {
        System.out.println("Jeton déjà séléctionné: " + tokenName);
    }

    public static void printTokenChosen(List<Token> tokenChosen) {
        var text = new StringBuilder();
        var separator = "";
        text.append("Jeton(s) Choisis: [");
        for (var token : tokenChosen){
            text.append(separator).append(token.name());
            separator = ", ";
        }
        text.append("]");
        System.out.println(text);
    }

    public static void printNotEnoughTokenChosen(int number) {
        System.out.println("Vous n'avez choisis que " + number + " / 3 jetons");
    }

    public static void printTokenChosenWithIndex(List<Token> tokenChosen) {
        var text = new StringBuilder();
        text.append("Jeton(s) Choisis: [");
        for (int i = 0; i < tokenChosen.size(); i++) {
            text.append("\n").append(i + 1).append(": ").append(tokenChosen.get(i).name());
        }
        text.append("\n]\nLequel voulez vous supprimer? (").append(tokenChosen.size() + 1).append(" pour annuler)");
        System.out.println(text);
    }

    public static void printNoTokenChosen() {
        System.out.println("Vous n'avez pas encore choisis de jeton !");
    }

    public static void printGround(Map<DeckName, List<Card>> grounds, Map<DeckName, Deck<Card>> decks){
        var print = new StringBuilder();
        for (var deck: grounds.keySet()) {
            printCardLeftToRight(grounds.get(deck), print, decks.get(deck), true, false);
        }
        System.out.println(print);
    }

    private static void printCardLeftToRight(List<Card> grounds, StringBuilder print, Deck<Card> deck, boolean deckSize, boolean withIndex) {
        var index = 1;
        var indexString = "  ";
        for (int i = 0; i < Constants.DISPLAY_SIZE; i++) {
            for (var card : grounds) {
                if(i == 1){
                    indexString = index + ":";
                    index++;
                }
                if(withIndex) print.append(indexString);
                print.append(card.getDisplay(i));
            }
            indexString = "  ";
            if(deckSize) print.append(deck.getDisplay(i));
            print.append("\n");
        }
    }

    public static void printChooseDeck(Map<DeckName, List<Card>> grounds) {
        var print = new StringBuilder();
        var i = 1;
        print.append("Choisissez le deck qui contient la carte que vous voulez réserver\n");
        for (var deckName: grounds.keySet()) {
            print.append(i).append(": ").append(deckName).append("\n");
        }
        System.out.print(print);
    }

    public static void printCards(List<Card> cards, int deckSize) {
        var print = new StringBuilder();
        print.append("Choisissez la carte ?(").append(cards.size() + 1).append("pour choisir de réserver la prochaine carte piochée)\n");
        printCardLeftToRight(cards, print, null, false, true);
        System.out.println(print);
    }
}
