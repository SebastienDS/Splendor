package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Token;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageManager {
    private final BufferedImage backGround;
    private final Map<String, Map<Token, BufferedImage>> cards;

    public ImageManager(Path backGround, Path cards, int width_screen, int height_screen) throws IOException {
        Objects.requireNonNull(backGround);
        this.backGround = loadImage(backGround, width_screen, height_screen);
        this.cards = new HashMap<>();
        if(cards != null) initCards(cards);
    }

    private void initCards(Path cards) {
        try (var paths = Files.walk(cards)) {
            paths.filter(Files::isRegularFile).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageManager(int width_screen, int height_screen) throws IOException {
        this(Path.of("resources", "images", "background.jpg"), null, width_screen, height_screen);
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

    public BufferedImage background() {
        return backGround;
    }
}
