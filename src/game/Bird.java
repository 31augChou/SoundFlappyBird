package game;

import java.awt.*;

public class Bird {

    public int x = 100;
    public int y = 250;

    public int width = 30;
    public int height = 24;

    private double velocity = 0;
    private final double gravity = 0.6;

    public void update() {
        velocity += gravity;
        y += velocity;
    }

    public void jump() {
        velocity = -8;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        // Body
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);

        // Eye
        g.setColor(Color.WHITE);
        g.fillOval(x + 18, y + 6, 6, 6);
        g.setColor(Color.BLACK);
        g.fillOval(x + 20, y + 8, 3, 3);

        // Beak
        g.setColor(Color.ORANGE);
        int[] bx = {x + width, x + width + 6, x + width};
        int[] by = {y + 8, y + 12, y + 16};
        g.fillPolygon(bx, by, 3);
    }
}
