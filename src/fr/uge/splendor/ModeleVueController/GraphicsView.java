package fr.uge.splendor.ModeleVueController;

import fr.uge.splendor.object.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class GraphicsView {

    public static BufferedImage loadImage(Path path) throws IOException {
        return load(path);
    }

    public static BufferedImage loadImage(Path path, int targetWidth, int targetHeight) throws IOException {
        return resize(loadImage(path), targetWidth, targetHeight);
    }

    private static BufferedImage load(Path path) throws IOException {
        try (var in = Files.newInputStream(path)) {
            return ImageIO.read(in);
        }
    }

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

    public static void drawImage(Graphics2D graphics, int x, int y, BufferedImage bufferedImage) {
        graphics.drawImage(bufferedImage, null, x, y);
    }

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
}
