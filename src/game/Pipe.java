package game;

import java.awt.*;
import java.util.Random;

public class Pipe {

    private int x;
    private final int width = 60;
    private final int gap = 160;
    private final int speed = 3;

    private int topHeight;
    private int bottomY;

    private final Random random = new Random();

    public Pipe() {
        reset();
    }

    public void reset() {
        x = 400;
        topHeight = 80 + random.nextInt(200);
        bottomY = topHeight + gap;
    }

    public void update() {
        x -= speed;
        if (x + width < 0) {
            reset();
        }
    }

    public boolean collides(Bird bird) {
        Rectangle topPipe = new Rectangle(x, 0, width, topHeight);
        Rectangle bottomPipe = new Rectangle(x, bottomY, width, 600);

        return topPipe.intersects(bird.getBounds()) ||
                bottomPipe.intersects(bird.getBounds());
    }

    public void draw(Graphics g) {
        // Pipe body
        g.setColor(new Color(34, 177, 76));
        g.fillRect(x, 0, width, topHeight);
        g.fillRect(x, bottomY, width, 600);

        // Pipe borders
        g.setColor(Color.BLACK);
        g.drawRect(x, 0, width, topHeight);
        g.drawRect(x, bottomY, width, 600);

        // Pipe heads (Flappy Bird style)
        g.setColor(new Color(0, 155, 0));
        g.fillRect(x - 5, topHeight - 15, width + 10, 15);
        g.fillRect(x - 5, bottomY, width + 10, 15);
    }
}
