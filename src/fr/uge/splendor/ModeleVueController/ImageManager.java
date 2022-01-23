package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Development;
import fr.uge.splendor.object.Noble;
import fr.uge.splendor.object.Token;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ImageManager {
    private final BufferedImage backGround;
    private final Map<String, Map<Token, BufferedImage>> cards;
    private final Map<String, BufferedImage> nobles;

    public ImageManager(Path backGround, int width_screen, int height_screen) throws IOException {
        Objects.requireNonNull(backGround);
        this.backGround = loadImage(backGround, width_screen, height_screen);
        this.cards = new HashMap<>();
        this.nobles = new HashMap<>();
    }

    public ImageManager(int width_screen, int height_screen) throws IOException {
        this(
                Path.of("resources", "images", "background.jpg"),
                width_screen,
                height_screen
        );
    }

    public void initCards(Path cards, Model gameData) {
        try (var paths = Files.walk(cards)) {
            paths.filter(Files::isRegularFile).forEach(
                    path-> {
                        try {
                            create_image(path, gameData);
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

    private void create_image(Path path, Model gameData) throws IOException {
        var token = Token.valueOf(path.getFileName().toString().split("[.]")[0]);
        var nameDirectory = path.getName(path.getNameCount() - 2).toString();
        if(cards.containsKey(nameDirectory)){
            cards.get(nameDirectory).put(token, loadCard(path, gameData));
            return;
        }
        cards.put(nameDirectory, new HashMap<>());
        cards.get(nameDirectory).put(token, loadCard(path, gameData));
    }

    public void initNoble(Path nobles, Model gameData) {
        try (var paths = Files.walk(nobles)) {
            paths.filter(Files::isRegularFile).forEach(
                    path-> {
                        try {
                            create_noble(path, gameData);
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

    private void create_noble(Path path, Model gameData) throws IOException {
        var name = path.getFileName().toString().split("[.]")[0];
        nobles.put(name, loadCard(path, gameData));
    }

    private BufferedImage loadCard(Path path, Model gameData) throws IOException {
        var length = gameData.getNumberOfDecks();
        var w = Integer.MAX_VALUE;
        var h = Math.min(GraphicsView.HEIGHT_SCREEN / (length + 1), 500);
        return loadImage(path, w, h);
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
        return cards.get(card.name()).get(card.bonus());
    }

    public BufferedImage get(Noble noble) {
        return nobles.get(noble.name());
    }
}
