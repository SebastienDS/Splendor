package fr.uge.splendor.object;

import java.awt.*;
import java.util.Objects;

/**
 * This method represents a Button
 */
public record Button(String label, int x, int y, int w, int h) {

    /**
     * Create an instance of button
     * @param label label of string
     * @param x x axis
     * @param y y axis
     * @param w width of button
     * @param h height of button
     */
    public Button {
        Objects.requireNonNull(label);
    }

    /**
     * This method return bounds of rectangle
     * @return bounds of rectangle
     */
    public Rectangle rect() {
        return new Rectangle(x - w / 2, y - h / 2, w, h);
    }

    /**
     * This method return pos of label
     * @param g graphics2D
     * @param font font of text
     * @return pos of label
     */
    public Point labelPos(Graphics2D g, Font font) {
        Objects.requireNonNull(g);
        Objects.requireNonNull(font);
        var rect = rect();
        var metrics = g.getFontMetrics(font);
        var bounds = metrics.getStringBounds(label, g);
        return new Point(rect.x + rect.width / 2 - (int)bounds.getCenterX(), rect.y + rect.height / 2 - (int)bounds.getCenterY());
    }
}
