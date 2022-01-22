package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Button;
import fr.uge.splendor.object.Player;
import fr.uge.splendor.object.TextField;
import fr.umlv.zen5.ApplicationContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;


public class GraphicsView {

    public static int WIDTH_SCREEN;
    public static int HEIGHT_SCREEN;

    /**
     * load an image and return it
     * @param path of the image
     * @return image loaded
     * @throws IOException if an I/O exception occur
     */
    public static BufferedImage loadImage(Path path) throws IOException {
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
    public static BufferedImage loadImage(Path path, int targetWidth, int targetHeight) throws IOException {
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
    public static BufferedImage resize(BufferedImage img, int targetWidth, int targetHeight) {
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
     * Draw an Image
     * @param graphics graphics2D
     * @param x placement x
     * @param y placement y
     * @param bufferedImage image to draw
     */
    public static void drawImage(Graphics2D graphics, int x, int y, BufferedImage bufferedImage) {
        graphics.drawImage(bufferedImage, null, x, y);
    }

    /**
     * Draw a Button
     * @param graphics graphics2D
     * @param button Button to draw
     */
    public static void drawButton(Graphics2D graphics, Button button) {
        var rect = button.rect();
        var font = new Font("Serif", Font.PLAIN, 30);
        var labelPos = button.labelPos(graphics, font);
        graphics.setFont(font);

        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(rect);

        graphics.setColor(Color.WHITE);
        graphics.drawString(
                button.label(),
                labelPos.x,
                labelPos.y
        );
    }

    /**
     * Draw a textField
     * @param graphics graphics2D
     * @param textField TextField to draw
     */
    public static void drawTextField(Graphics2D graphics, TextField textField) {
        var rect = textField.rect();
        var font = new Font("Serif", Font.PLAIN, 30);
        var labelPos = textField.labelPos(graphics, font);
        graphics.setFont(font);

        graphics.setColor(Color.WHITE);
        graphics.fill(rect);

        graphics.setColor(Color.DARK_GRAY);
        graphics.drawString(
                textField.label(),
                labelPos.x,
                labelPos.y
        );
    }

    /**
     * Draw name of all player
     * @param graphics graphics2D
     * @param players list of all player
     * @param height height of screen
     */
    public static void drawPlayers(Graphics2D graphics, List<Player> players, int height) {
        graphics.setColor(Color.BLACK);
        graphics.drawString(
                "Joueurs: ",
                50,
                height / 7
        );
        for (int i = 0; i < players.size(); i++) {
            graphics.drawString(
                    players.get(i).getName(),
                    100,
                    height / 7  + (height / 15) * (i + 1)
            );
        }
    }

    public static void setWidth(int width) {
        WIDTH_SCREEN = width;
    }

    public static void setHeight(int height) {
        HEIGHT_SCREEN = height;
    }

    public static void drawStartingMenu(ApplicationContext context, List<Button> buttons) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fill(new Rectangle(0, 0, GraphicsView.WIDTH_SCREEN, GraphicsView.HEIGHT_SCREEN));
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });
    }

    public static void drawModeMenu(ApplicationContext context, List<Button> buttons) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fill(new Rectangle(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN));
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });
    }

    public static void drawPlayerMenu(ApplicationContext context, List<Button> buttons, Model gameData) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fill(new Rectangle(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN));
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
            GraphicsView.drawPlayers(graphics, gameData.getPlayers(), HEIGHT_SCREEN);
        });
    }

    public static void drawChangeNameMenu(ApplicationContext context, TextField textField, List<Button> buttons) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fill(new Rectangle(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN));
            GraphicsView.drawTextField(graphics, textField);
            buttons.forEach(button -> GraphicsView.drawButton(graphics, button));
        });
    }

    public static void drawChoosePlayerMenu(ApplicationContext context, List<Button> buttons) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fill(new Rectangle(0, 0, WIDTH_SCREEN, HEIGHT_SCREEN));
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });
    }
}
