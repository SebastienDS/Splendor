package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Constants;
import fr.uge.splendor.object.Development;
import fr.uge.splendor.object.Noble;
import fr.uge.splendor.object.Token;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * This class manage all images of the games
 */
public class ImageManager {

    /**
     * Background image
     */
    private final BufferedImage backGround;
    /**
     * Title image
     */
    private final BufferedImage title;
    /**
     * Background win
     */
    private final BufferedImage backGroundWin;
    /**
     * String winner
     */
    private final BufferedImage winString;
    /**
     * Card background Image
     */
    private BufferedImage cardBackGround;
    /**
     * All cards images mapped by name and token
     */
    private final Map<String, Map<Token, BufferedImage>> cards;
    /**
     * All nobles image mapped by name
     */
    private final Map<String, BufferedImage> nobles;

    /**
     * Create an instance of image manager
     * @param backGround background image path
     * @param title title image path
     * @param backGroundWin background winning image path
     * @param winString string win
     * @param width_screen width screen
     * @param height_screen height screen
     * @throws IOException if an I/O Exception occur
     */
    public ImageManager(Path backGround, Path title, Path backGroundWin, Path winString, int width_screen, int height_screen) throws IOException {
        Objects.requireNonNull(backGround);
        Objects.requireNonNull(title);
        Objects.requireNonNull(backGroundWin);
        Objects.requireNonNull(winString);
        this.backGround = loadImage(backGround, width_screen, height_screen);
        this.title = loadImage(title, width_screen / 3, height_screen / 15);
        this.backGroundWin = loadImage(backGroundWin, width_screen, height_screen);
        this.winString = loadImage(winString, 2 * width_screen / 3, height_screen / 3);
        this.cards = new HashMap<>();
        this.nobles = new HashMap<>();
    }

    /**
     * Create an instance of image manager with default background and title path value
     * @param width_screen width of screen
     * @param height_screen height of screen
     * @throws IOException if an I/O Exception occur
     */
    public ImageManager(int width_screen, int height_screen) throws IOException {
        this(
                Path.of("resources", "images", "background.jpg"),
                Path.of("resources", "images", "title.png"),
                Path.of("resources", "images", "winner.jpg"),
                Path.of("resources", "images", "winString.png"),
                width_screen,
                height_screen
        );
    }

    /**
     * Initialise all cards of the game with cards the directory with all the cards
     * @param cards directory with all the cards
     * @param gameData data of the game
     */
    public void initCards(Path cards, Model gameData) {
        Objects.requireNonNull(cards);
        Objects.requireNonNull(gameData);
        var length = gameData.getNumberOfDecks();
        var w = GraphicsView.WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 2));
        var h = Math.min(GraphicsView.HEIGHT_SCREEN / (length + 1), 500);
        try (var paths = Files.walk(cards)) {
            paths.filter(Files::isRegularFile).forEach(
                    path-> {
                        try {
                            create_image(path, w, h);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                            System.exit(1);
                        }
                    }
            );
            cardBackGround = loadImage(Path.of("resources", "images", "cardsBack.png"), w, h);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Create an image of the path with width and height value and add it to cards map
     * @param path path of image
     * @param width width of image
     * @param height height of image
     * @throws IOException if an I/O Exception occur
     */
    private void create_image(Path path, int width, int height) throws IOException {
        var token = Token.valueOf(path.getFileName().toString().split("[.]")[0]);
        var nameDirectory = path.getName(path.getNameCount() - 2).toString();
        if(cards.containsKey(nameDirectory)){
            cards.get(nameDirectory).put(token, loadImage(path, width, height));
            return;
        }
        cards.put(nameDirectory, new HashMap<>());
        cards.get(nameDirectory).put(token, loadImage(path, width, height));
    }

    /**
     * Initialise all nobles of the game with nobles the directory with all images
     * @param nobles directory with all nobles images
     * @param gameData data of the game
     */
    public void initNoble(Path nobles, Model gameData) {
        Objects.requireNonNull(nobles);
        Objects.requireNonNull(gameData);
        var length = gameData.getNumberOfDecks();
        var w = Integer.MAX_VALUE;
        var h = Math.min(GraphicsView.HEIGHT_SCREEN / (length + 1), 500);
        try (var paths = Files.walk(nobles)) {
            paths.filter(Files::isRegularFile).forEach(
                    path-> {
                        try {
                            create_noble(path, w, h);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                            System.exit(1);
                        }
                    }
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Create and put image of noble in nobles list
     * @param path path of image
     * @param width width of image
     * @param height height of image
     * @throws IOException if an I/O Exception occur
     */
    private void create_noble(Path path, int width, int height) throws IOException {
        var name = path.getFileName().toString().split("[.]")[0];
        nobles.put(name, loadImage(path, width, height));
    }

    /**
     * load an image and return it
     * @param path of the image
     * @return image loaded
     * @throws IOException if an I/O exception occur
     */
    private BufferedImage loadImage(Path path) throws IOException {
        return load(path);
    }

    /**
     * Load an image, resize it and then return it. Targeted width and targeted height can be inferior to
     * parameter specified because ratio is kept
     * @param path path of the image
     * @param targetWidth width of the image
     * @param targetHeight height of the image
     * @return image resized
     * @throws IOException if an I/O exception occur
     */
    private BufferedImage loadImage(Path path, int targetWidth, int targetHeight) throws IOException {
        return resize(loadImage(path), targetWidth, targetHeight);
    }

    /**
     * load an image and return it
     * @param path Path of image
     * @return image loaded
     */
    private static BufferedImage load(Path path) throws IOException {
        try (var in = Files.newInputStream(path)) {
            return ImageIO.read(in);
        }
    }

    /**
     * resize an image and return it. Targeted width and targeted height can be inferior to
     * parameter specified because ratio is kept.
     * @param img Image to resize
     * @param targetWidth new size width with scale
     * @param targetHeight new size height with scale
     * @return image resized
     */
    private BufferedImage resize(BufferedImage img, int targetWidth, int targetHeight) {
        var scaleW = (double) targetWidth / (double) img.getWidth();
        var scaleH = (double) targetHeight / (double) img.getHeight();
        var scale = Math.min(scaleW, scaleH);

        var result = new BufferedImage((int) (img.getWidth() * scale),
                (int) (img.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);

        var g = result.createGraphics();
        g.drawImage(img, 0, 0, result.getWidth(), result.getHeight(), null);
        g.dispose();

        return result;
    }

    /**
     * This methode return background image
     * @return background image
     */
    public BufferedImage background() {
        return backGround;
    }

    /**
     * This methode return the image of the card
     * @param card card associated to the image
     * @return image associated to the card
     */
    public BufferedImage get(Development card) {
        Objects.requireNonNull(card);
        return cards.get(card.name()).get(card.bonus());
    }

    /**
     * This methode return the image of the noble
     * @param noble noble associated to the image
     * @return image of the noble
     */
    public BufferedImage get(Noble noble) {
        Objects.requireNonNull(noble);
        return nobles.get(noble.name());
    }

    /**
     * this method return the title image
     * @return title image
     */
    public BufferedImage title() {
        return title;
    }

    /**
     * This method return the card background image
     * @return the card background image
     */
    public BufferedImage cardBackGround() {
        return cardBackGround;
    }

    /**
     * This method return image of background of win
     * @return image of background of win
     */
    public BufferedImage backgroundWin() {
        return backGroundWin;
    }

    /**
     * This method return image of the string of win
     * @return image of the string of win
     */
    public BufferedImage stringWin() {
        return winString;
    }
}
