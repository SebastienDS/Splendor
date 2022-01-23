package fr.uge.splendor.object;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;

/**
 * This method represents a TextField
 */
public class TextField{

    /**
     * This represents the label of the text field
     */
    private final StringBuilder label;
    /**
     * x axis
     */
    private final int x;
    /**
     * y axis
     */
    private final int y;
    /**
     * width of text field
     */
    private final int w;
    /**
     * height of text field
     */
    private final int h;

    /**
     * Create an instance of text field
     * @param label label of the text field
     * @param x x axis
     * @param y y axis
     * @param w width of the text field
     * @param h height of the text field
     */
    public TextField(String label, int x, int y, int w, int h){
        Objects.requireNonNull(label);
        this.label = new StringBuilder();
        this.label.append(label);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * This method return bounds of text field as a rectangle
     * @return bounds of text field as a rectangle
     */
    public Rectangle rect() {
        return new Rectangle(x - w / 2, y - h / 2, w, h);
    }

    /**
     * This method return position of label
     * @param g graphics2D
     * @param font font of the text
     * @return position of label
     */
    public Point labelPos(Graphics2D g, Font font) {
        Objects.requireNonNull(g);
        Objects.requireNonNull(font);
        var rect = rect();
        var metrics = g.getFontMetrics(font);
        var bounds = metrics.getStringBounds(label.toString(), g);
        return new Point(rect.x + rect.width / 2 - (int)bounds.getCenterX(), rect.y + rect.height / 2 - (int)bounds.getCenterY());
    }

    /**
     * This method return the label of the text field
     * @return label of the text field
     */
    public String label() {
        return label.toString();
    }

    /**
     * This method add the string to the label
     * @param name string to add to label
     */
    public void add(String name) {
        Objects.requireNonNull(name);
        if(label.length() > 16) return;
        if(label.length() == 0){
            label.append(name);
            return;
        }
        label.append(name.toLowerCase(Locale.ROOT));
    }

    /**
     * This method remove the last character of label
     */
    public void remove() {
        if(label.length() > 0)
            label.delete(label.length() - 1, label.length());
    }
}

