package ModeleVueController;

import object.Player;
import object.Token;

import java.util.List;

public class View {
    public static void printStartingMenu() {
        System.out.println("Choississez un menu:");
        System.out.println("0: Menu des Joueurs");
        System.out.println("1: Choix du jeu");
        System.out.println("2: Lancer la partie");
    }

    public static void printGameModeMenu() {
        System.out.println("Choississez le mode de jeu:");
        System.out.println("1: Mode de jeu partie 1");
        System.out.println("2: Mode de jeu partie 2 / 3");
    }

    public static void printPlayerMenu(Model gameData) {
        System.out.println("Joueur Actuelle:");
        gameData.getPlayers().forEach(player -> System.out.println(player.getName()));
        System.out.println("Que voulez vous faire?");
        System.out.println("0: Modifier un nom");
        System.out.println("1: Ajouter un joueur");
        System.out.println("2: Supprimer un joueur");
        System.out.println("3: Quitter le menu joueur");

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
            text.append("\n").append(i).append(": ").append(players.get(i).getName());
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
        System.out.println(playerPlaying.getName() + " " + playerPlaying.getWallet());
    }

    public static void printFirstChoicePlayer() {
        var text = new StringBuilder();
        text.append("Que voulez vous faire ?\n").append("1: Prendre 2 jetons de la même couleur\n");
        text.append("2: Prendre 3 jetons de couleurs différentes\n").append("3: Réserver 1 carte développement et prendre 1 or\n");
        text.append("4: Acheter 1 carte développement face visible au centre de la table ou préalablement réservée.");
        System.out.println(text);
    }

    public static void printTokens(Model gameData) {
        var text = new StringBuilder();
        text.append("Quelle jeton?\n");
        var gameTokens = gameData.getGameTokens();
        var iteration = 1;
        for(var token: Token.cardValues()){
            text.append(iteration).append(": ").append(token).append("(quantity:").append(gameTokens.get(token)).append(")\n");
            iteration++;
        }
        System.out.println(text);
    }

    public static void printMenuChooseToken() {
        var text = new StringBuilder();
        text.append("Que voulez vous faire?\n").append("1: Choisir un jeton\n").append("2: Retirer un jeton choisis\n")
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
}
