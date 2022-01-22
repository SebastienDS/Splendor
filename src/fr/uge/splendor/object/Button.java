package fr.uge.splendor.object;

import java.awt.*;
import java.util.Objects;

public record Button(String label, int x, int y, int w, int h) {

    public Button {
        Objects.requireNonNull(label);
    }

    public Rectangle rect() {
        return new Rectangle(x - w / 2, y - h / 2, w, h);
    }

    public Point labelPos(Graphics2D g, Font font) {
        var rect = rect();
        var metrics = g.getFontMetrics(font);
        var bounds = metrics.getStringBounds(label, g);
        return new Point(rect.x + rect.width / 2 - (int)bounds.getCenterX(), rect.y + rect.height / 2 - (int)bounds.getCenterY());
    }
}
