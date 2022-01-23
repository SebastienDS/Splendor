package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.*;
import fr.uge.splendor.object.Button;
import fr.uge.splendor.object.TextField;
import fr.umlv.zen5.ApplicationContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
                drawTitle(graphics, images.title());
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

    private static void drawTitle(Graphics2D graphics, BufferedImage title){
        graphics.drawImage(title, WIDTH_SCREEN / 3, 0, WIDTH_SCREEN / 3, HEIGHT_SCREEN / 10 + 10, null);
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

    public static void drawGame(ApplicationContext context, Model gameData, ImageManager images, List<Button> buttons, ActionManager actionManager) {
        context.renderFrame(graphics -> {
            try {
                var length = gameData.getNumberOfDecks();
                drawBackGround(graphics, images.background());
                drawDecks(graphics, gameData, images, length);
                drawTokens(graphics, gameData);
                drawReservedCard(graphics, gameData.getPlayerPlaying().getCardReserved(), length, images);
                drawPlayers(graphics, gameData);
                drawButtons(graphics, buttons, actionManager, gameData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void drawPlayers(Graphics2D graphics, Model gameData) {
        var players = gameData.getPlayers();
        var font = new Font("Serif", Font.BOLD, 25);
        graphics.setFont(font);
        var width = WIDTH_SCREEN / 5;
        var height = HEIGHT_SCREEN / (2 * players.size());
        for (int i = 0; i < players.size(); i++) {
            drawPlayer(graphics, players.get(i), 4 * width, i * height, width, height, font,
                    gameData.getGameMode(), gameData.isPlaying(i));
        }
    }

    private static void drawPlayer(Graphics2D graphics, Player player, int x, int y, int width, int height,
                                   Font font, int gameMode, boolean isPlaying) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(x, y, width, height - 5);
        drawString(graphics, player.getName(), x, y, width, height / 3, Color.black, font);
        drawTokensPlayer(graphics, player, x, y, width, height, gameMode);
        drawString(graphics, String.valueOf(player.getPrestige()), x, y, width / 5, height / 4, Color.black, font);
        graphics.setPaint(Color.red);
        int[] xPoint = {x - 10, x -50, x - 50};
        int[] yPoint = {y + height / 2, y + height / 3, y + 2 * height / 3};
        if(isPlaying) graphics.fillPolygon(xPoint, yPoint, 3);
    }

    private static void drawTokensPlayer(Graphics2D graphics, Player player, int x, int y, int width, int height, int gameMode) {
        var tokens = Token.values();
        int indexAdjustment = 0;
        var sizeToken = width / (tokens.length - 1);
        if(sizeToken >= height / 4) sizeToken -= sizeToken - height / 4 - 5;
        var padding = (width - (sizeToken * (tokens.length - 1)))/ (tokens.length - 1);
        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i] == Token.NONE || tokens[i] == Token.GOLD && gameMode == 1){
                indexAdjustment++;
                continue;
            }
            int x1 = x + (i - indexAdjustment) * sizeToken + padding * (i - indexAdjustment);
            drawTokenWithNumber(graphics, x1, y + height / 3,
                    sizeToken, sizeToken, tokens[i], player.getWallet().get(tokens[i]));
            drawTokenWithNumber(graphics, x1, y + 2 * height / 3,
                    sizeToken, sizeToken, tokens[i], player.getBonus().get(tokens[i]));
        }
    }

    private static void drawReservedCard(Graphics2D graphics, List<Development> cards, int length, ImageManager images){
        for (int i = 0; i < cards.size(); i++) {
            drawCard(graphics, length, 7 + i, 2, images, cards.get(i));
        }
    }

    private static void drawTokens(Graphics2D graphics, Model gameData) {
        var tokensGame = gameData.getGameTokens().tokens();
        var i = 0;
        for (var token : tokensGame.keySet()) {
            if(token == Token.GOLD && gameData.getGameMode() == 1) continue;
            drawTokenWithNumber(graphics,
                    WIDTH_SCREEN / 2,
                    HEIGHT_SCREEN / 3 + i * HEIGHT_SCREEN / 14,
                    HEIGHT_SCREEN / 15,
                    HEIGHT_SCREEN /15,
                    token,
                    tokensGame.get(token)
            );
            i++;
        }
    }

    private static void drawTokenWithNumber(Graphics2D graphics, int x, int y, int width, int height, Token token, int number) {
        drawToken(graphics, x, y, width, height, token);
        var font = new Font("Serif", Font.BOLD, 35);
        drawStringOutlined(graphics, String.valueOf(number), x, y, width, height, Color.WHITE, font);
    }

    private static void drawButtons(Graphics2D graphics,  List<Button> buttons, ActionManager actionManager, Model gameData) {
        switch (actionManager.getAction()) {
            case CARD -> {
                if (gameData.getPlayerPlaying().canBuy(actionManager.getSelectedCard())) GraphicsView.drawButton(graphics, buttons.get(0));
                if (gameData.getPlayerPlaying().canReserve()) GraphicsView.drawButton(graphics, buttons.get(1));
            }
            case DECK -> GraphicsView.drawButton(graphics, buttons.get(1));
            case TOKEN -> {
                GraphicsView.drawButton(graphics, buttons.get(2));
                GraphicsView.drawButton(graphics, buttons.get(3));
            }
        }
    }

    private static void drawDecks(Graphics2D graphics, Model gameData, ImageManager images, int length) throws IOException {
        for (var key : gameData.getGrounds().keySet()) {
            drawGrounds(graphics, gameData, images, length, key);
        }
        drawNobles(graphics, gameData, images, length);
    }

    private static void drawNobles(Graphics2D graphics, Model gameData, ImageManager images, int length) throws IOException {
        var nobles = gameData.getNobles();
        for (int i = 0; i < nobles.size(); i++) {
            drawImage(graphics, length, i, 0, images.get(nobles.get(i)));
            drawNobleCharacteristic(graphics, nobles.get(i), images.get(nobles.get(i)), i, length);
        }
    }

    private static void drawGrounds(Graphics2D graphics, Model gameData, ImageManager images, int length, int index) {
        var cards = gameData.getGrounds().get(index);
        for (int i = 0; i < cards.size(); i++) {
            drawCard(graphics, length, i, index, images, cards.get(i));
        }
        drawImage(graphics, length, cards.size(), index, images.cardBackGround());
        var stringSize = String.valueOf(gameData.getDecks().get(index).size());
        drawSizeDeck(graphics, stringSize, length, images.get(cards.get(0)), cards.size(), index);
    }

    private static void drawCard(Graphics2D graphics, int length, int indexWidth, int indexHeight, ImageManager images,
                                    Development card) {
        drawImage(graphics, length, indexWidth, indexHeight, images.get(card));
        drawCardCharacteristic(graphics, card, images.get(card), indexWidth, indexHeight, length);
    }

    private static void drawSizeDeck(Graphics2D graphics, String stringSize, int length, BufferedImage image,
                                     int indexWidth, int indexHeight) {
        var font = new Font("Serif", Font.BOLD, 50);
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY * indexHeight + spacingY / 2 - image.getHeight() / 2;
        drawStringOutlined(graphics, stringSize, x, y, image.getWidth(), image.getHeight(), Color.red, font);
    }

    private static void drawStringOutlined(Graphics2D graphics, String str, int x, int y, int width,
                                           int height, Color color, Font font){
        var metrics = graphics.getFontMetrics(font);
        var bounds = metrics.getStringBounds(str, graphics);
        x = (int)(x + width / 2 - bounds.getCenterX());
        y = (int)(y + height / 2 - bounds.getCenterY());
        graphics.setFont(font);
        graphics.setColor(Color.black);
        graphics.drawString(str, x + 1, y - 1);
        graphics.drawString(str, x + 1, y + 1);
        graphics.drawString(str, x - 1, y - 1);
        graphics.drawString(str, x - 1, y + 1);
        graphics.setColor(color);
        graphics.drawString(str, x, y);
    }

    private static void drawCardCharacteristic(Graphics2D graphics, Development card, BufferedImage image,
                                                int indexWidth, int indexHeight, int length) {
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY * indexHeight + spacingY / 2 - image.getHeight() / 2;
        var font = new Font("Serif", Font.BOLD, 15);
        drawPrestige(graphics, String.valueOf(card.prestige()), image, x, y, font);
        if(!(card.bonus() == Token.NONE)) drawBonus(graphics, card.bonus(), x, y, image);
        var cost = card.cost().tokens();
        var index = 0;
        for (var token: cost.keySet()) {
            drawCost(graphics, token, cost.get(token), x, y, index, image.getWidth(), image.getHeight(), font);
            index += 1;
        }
    }

    private static void drawCost(Graphics2D graphics, Token token, int price, int x, int y,
                                 int index, int width, int height, Font font) {
        graphics.setPaint(Color.LIGHT_GRAY);
        int y1 = y + (6 - index) * height / 7;
        graphics.fillRect(x, y1, width / 5, height / 7);
        drawToken(graphics, x, y1, width / 5, height / 7, token);
        var color = (token == Token.ONYX)? Color.white : Color.black;
        drawString(graphics, String.valueOf(price), x, y1, width / 5, height / 7, color, font);
    }

    private static void drawBonus(Graphics2D graphics, Token bonus, int x, int y, BufferedImage image) {
        graphics.setPaint(Color.LIGHT_GRAY);
        graphics.fillRect(x + 4 * image.getWidth() /5, y, image.getWidth() / 5, image.getHeight() / 7);
        drawToken(graphics, x + 4 * image.getWidth() / 5, y, image.getWidth()/5, image.getHeight() / 7, bonus);
    }

    private static void drawToken(Graphics2D graphics, int x, int y, int width, int height, Token token){
        graphics.setPaint(token.getColor());
        graphics.fillOval(x + 1, y + 1, width - 2, height - 2);
    }

    private static void drawNobleCharacteristic(Graphics2D graphics, Noble noble, BufferedImage image,
                                               int indexWidth, int length) {
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY / 2 - image.getHeight() / 2;
        var font = new Font("Serif", Font.BOLD, 15);
        drawPrestige(graphics, String.valueOf(noble.prestige()), image, x, y, font);
        var index = 0;
        var cost = noble.cost();
        for (var token: cost.keySet()) {
            drawCost(graphics, token, cost.get(token), x, y, index, image.getWidth(), image.getHeight(), font);
            index += 1;
        }
    }

    private static void drawPrestige(Graphics2D graphics, String prestige, BufferedImage image, int x, int y, Font font) {
        graphics.setPaint(Color.LIGHT_GRAY);
        graphics.fillRect(x, y, image.getWidth() / 5, image.getHeight() / 7);
        drawString(graphics, prestige, x, y, image.getWidth() / 5, image.getHeight() / 7, Color.black, font);
    }

    private static void drawString(Graphics2D graphics, String str, int x, int y,
                                   int width, int height, Color color,  Font font) {
        graphics.setFont(font);
        var metrics = graphics.getFontMetrics(font);
        var bounds = metrics.getStringBounds(str, graphics);
        graphics.setPaint(color);
        graphics.drawString(str, (int)(x + width / 2 - bounds.getCenterX()), (int)(y + height / 2 - bounds.getCenterY()));
    }

    private static void drawImage(Graphics2D graphics, int length, int indexWidth, int indexHeight, BufferedImage image){
        var spacingX = WIDTH_SCREEN / (2 * (Constants.DRAW_NUMBER + 1));
        var spacingY = HEIGHT_SCREEN / length;
        var x = spacingX * indexWidth + spacingX / 2 - image.getWidth() / 2;
        var y = spacingY * indexHeight + spacingY / 2 - image.getHeight() / 2;
        GraphicsView.drawImage(graphics, x, y, image);
    }
}
