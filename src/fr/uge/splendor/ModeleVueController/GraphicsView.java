package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;
import fr.uge.splendor.object.Button;
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
        graphics.setPaint(Color.LIGHT_GRAY);
        graphics.fillRect(25, height / 7 - 25, 350, height / 7 + height / 15 * (players.size() - 1));
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

    /**
     * Draw elements of starting menu with the background
     * @param context display context
     * @param buttons all buttons of starting menu
     */
    public static void drawStartingMenu(ApplicationContext context, List<Button> buttons, ImageManager images) {
        context.renderFrame(graphics -> {
            try {
                drawBackGround(graphics, images.background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });
    }

    /**
     * Draw elements of mode menu with the background
     * @param context display context
     * @param buttons all buttons of mode menu
     */
    public static void drawModeMenu(ApplicationContext context, List<Button> buttons, ImageManager images) {
        context.renderFrame(graphics -> {
            try {
                drawBackGround(graphics, images.background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });
    }

    /**
     * Draw elements of player menu with the background
     * @param context display context
     * @param buttons all buttons of player menu
     */
    public static void drawPlayerMenu(ApplicationContext context, List<Button> buttons,
                                      Model gameData, ImageManager images) {
        context.renderFrame(graphics -> {
            try {
                drawBackGround(graphics, images.background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
            GraphicsView.drawPlayers(graphics, gameData.getPlayers(), HEIGHT_SCREEN);
        });
    }

    /**
     * Draw elements of change name menu with the background
     * @param context display context
     * @param buttons all buttons of change name menu
     */
    public static void drawChangeNameMenu(ApplicationContext context, TextField textField, List<Button> buttons,
                                          ImageManager images) {
        context.renderFrame(graphics -> {
            try {
                drawBackGround(graphics, images.background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            GraphicsView.drawTextField(graphics, textField);
            buttons.forEach(button -> GraphicsView.drawButton(graphics, button));
        });
    }

    /**
     * Draw backGround
     * @param graphics graphics2D
     * @param background background image
     */
    private static void drawBackGround(Graphics2D graphics, BufferedImage background) throws IOException {
        graphics.drawImage(background, 0, 0, WIDTH_SCREEN, HEIGHT_SCREEN, null);
    }

    /**
     * Draw elements of choose player menu with the background
     * @param context display context
     * @param buttons all buttons of choose player menu
     */
    public static void drawChoosePlayerMenu(ApplicationContext context, List<Button> buttons, ImageManager images) {
        context.renderFrame(graphics -> {
            try {
                drawBackGround(graphics, images.background());
            } catch (IOException e) {
                e.printStackTrace();
            }
            buttons.forEach(b -> GraphicsView.drawButton(graphics, b));
        });
    }

    public static void drawGame(ApplicationContext context, Model gameData, ImageManager images) {
        context.renderFrame(graphics -> {
            try {
                drawBackGround(graphics, images.background());
                drawDecks(graphics, gameData, images);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void drawDecks(Graphics2D graphics, Model gameData, ImageManager images) throws IOException {
        var length = gameData.getNumberOfDecks();
        for (var key : gameData.getGrounds().keySet()) {
            drawCards(graphics, gameData, images, length, key);
        }
        drawNobles(graphics, gameData, images, length);
    }

    private static void drawNobles(Graphics2D graphics, Model gameData, ImageManager images, int length) throws IOException {
        var nobles = gameData.getNobles();
        for (int i = 0; i < nobles.size(); i++) {
            drawImage(graphics, length, i, 0, images.get(nobles.get(i)));
        }
    }

    private static void drawCards(Graphics2D graphics, Model gameData, ImageManager images, int length, int index) {
        var cards = gameData.getGrounds().get(index);
        for (int i = 0; i < cards.size(); i++) {
            drawImage(graphics, length, i, index, images.get(cards.get(i)));
            drawCardCharacteristic(graphics, cards.get(i), images.get(cards.get(i)), i, index, length);
        }
    }

    private static void drawCardCharacteristic(Graphics2D graphics, Development card, BufferedImage image,
                                                int indexWidth, int indexHeight, int length) {
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY * indexHeight + spacingY / 2 - image.getHeight() / 2;
        var font = new Font("Serif", Font.BOLD, 15);
        drawPrestige(graphics, String.valueOf(card.prestige()), image, x, y, font);
        drawBonus(graphics, card.bonus(), x, y, image);
    }

    private static void drawBonus(Graphics2D graphics, Token bonus, int x, int y, BufferedImage image) {
        graphics.fillRect(x + image.getWidth() /5, y, image.getWidth() / 5, image.getHeight() / 7);
        graphics.setPaint(bonus.getColor());
    }

    private static void drawNobleCharacteristic(Graphics2D graphics, Noble noble, BufferedImage image,
                                               int indexWidth, int indexHeight, int length) {
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY * indexHeight + spacingY / 2 - image.getHeight() / 2;
        var font = new Font("Serif", Font.BOLD, 15);
        drawPrestige(graphics, String.valueOf(noble.prestige()), image, x, y, font);
    }

    private static void drawPrestige(Graphics2D graphics, String prestige, BufferedImage image, int x, int y, Font font) {
        graphics.setPaint(Color.LIGHT_GRAY);
        graphics.fillRect(x, y, image.getWidth() / 5, image.getHeight() / 7);
        drawString(graphics, prestige, x, y, image.getWidth() / 5, image.getHeight() / 7, font);
    }

    private static void drawString(Graphics2D graphics, String prestige, int x, int y, int width, int height,  Font font) {
        graphics.setFont(font);
        var metrics = graphics.getFontMetrics(font);
        var bounds = metrics.getStringBounds(prestige, graphics);
        graphics.setColor(Color.BLACK);
        graphics.drawString(prestige, (int)(x + width / 2 - bounds.getCenterX()), (int)(y + height / 2 - bounds.getCenterY()));
    }

    private static void drawImage(Graphics2D graphics, int length, int indexWidth, int indexHeight, BufferedImage image){
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY * indexHeight + spacingY / 2 - image.getHeight() / 2;
        GraphicsView.drawImage(graphics, x, y, image);
    }
}
