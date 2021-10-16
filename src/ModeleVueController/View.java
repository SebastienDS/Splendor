package ModeleVueController;

import java.lang.reflect.Modifier;

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
}
