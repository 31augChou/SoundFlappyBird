package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;

    private Timer timer;
    private Bird bird;
    private Pipe pipe;
    private AudioInput audio;

    private boolean gameOver = false;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.cyan);
        setFocusable(true);

        bird = new Bird();
        pipe = new Pipe();
        audio = new AudioInput();

        timer = new Timer(20, this); // ~50 FPS

        // Keyboard listener (for restart + optional backup control)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                }

                // Optional keyboard jump (backup)
                if (!gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    bird.jump();
                }
            }
        });
    }

    public void startGame() {
        requestFocusInWindow();
        audio.start();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            repaint();
            return;
        }

        // 🎤 Sound control
        if (audio.isLoud()) {
            bird.jump();
        }

        bird.update();
        pipe.update();

        // Collision detection
        if (pipe.collides(bird) || bird.y > HEIGHT || bird.y < 0) {
            gameOver();
        }

        repaint();
    }

    private void gameOver() {
        gameOver = true;
        timer.stop();
        audio.stop();
    }

    private void restartGame() {
        bird = new Bird();
        pipe = new Pipe();
        audio = new AudioInput();
        gameOver = false;

        audio.start();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw game objects
        bird.draw(g);
        pipe.draw(g);

        // Game over overlay
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, WIDTH, HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", 90, 250);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Press R to Restart", 115, 300);
            g.drawString("Clap or make sound to fly", 80, 330);
        }
    }
}
