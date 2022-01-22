package fr.uge.splendor.object;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class TextField{

    private StringBuilder label;
    private final int x;
    private final int y;
    private final int w;
    private final int h;

    public TextField(String label, int x, int y, int w, int h){
        Objects.requireNonNull(label);
        this.label = new StringBuilder();
        this.label.append(label);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle rect() {
        return new Rectangle(x - w / 2, y - h / 2, w, h);
    }

    public Point labelPos(Graphics2D g, Font font) {
        var rect = rect();
        var metrics = g.getFontMetrics(font);
        var bounds = metrics.getStringBounds(label.toString(), g);
        return new Point(rect.x + rect.width / 2 - (int)bounds.getCenterX(), rect.y + rect.height / 2 - (int)bounds.getCenterY());
    }

    public String label() {
        return label.toString();
    }

    public void add(String name) {
        if(label.length() > 16) return;
        if(label.length() == 0){
            label.append(name);
            return;
        }
        label.append(name.toLowerCase(Locale.ROOT));
    }

    public void remove() {
        label.delete(label.length() - 1, label.length());
    }
}

