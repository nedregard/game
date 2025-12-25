package graphics;

import controls.KeyHandler;
import entities.Player;
import world.Scene;
import world.WorldHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 640;
    public static final int CELL_SIZE = 40;
    public static final int FPS = 24;
    WorldHandler worldHandler;
    private Player player;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        this.addKeyListener(keyHandler);

        worldHandler = new WorldHandler();
        worldHandler.setScene(new Scene("test", 25, 20));

        player = new Player(WIDTH, HEIGHT);
        player.spawn(400, 400);

        startGameThread();
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Draw grid
        g2.setColor(Color.GRAY);
        for (int x = 0; x < WIDTH; x += CELL_SIZE) {
            g2.drawLine(x, 0, x, HEIGHT);
        }
        for (int y = 0; y < HEIGHT; y += CELL_SIZE) {
            g2.drawLine(0, y, WIDTH, y);
        }

        worldHandler.getScene().draw(g2);

        drawSprites(g2);
        player.draw(g2);
    }

    private void drawSprites(Graphics2D g2) {
        drawTree(g2, 0, 0);
    }

    private void drawTree(Graphics g2, int col, int row) {
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;

        // Draw trunk
        int trunkWidth = CELL_SIZE / 6;
        int trunkHeight = CELL_SIZE / 3;
        int trunkX = x + (CELL_SIZE - trunkWidth) / 2;
        int trunkY = y + CELL_SIZE - trunkHeight - 4;
        g2.setColor(new Color(139, 69, 19)); // Brown
        g2.fillRect(trunkX, trunkY, trunkWidth, trunkHeight);

        // Draw leaves
        int leavesWidth = CELL_SIZE - 8;
        int leavesHeight = CELL_SIZE / 2;
        int leavesX = x + (CELL_SIZE - leavesWidth) / 2;
        int leavesY = y + 4;
        g2.setColor(new Color(34, 139, 34)); // Forest Green
        g2.fillOval(leavesX, leavesY, leavesWidth, leavesHeight);
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (keyHandler.leftPressed) {
            player.applyLeftMovement();
        }
        if (keyHandler.rightPressed) {
            player.applyRightMovement();
        }
        if (keyHandler.downPressed) {
            player.applyDownMovement();
        }
        if (keyHandler.upPressed) {
            player.applyUpMovement();
        }
        if (keyHandler.firePressed) {
            player.fire();
        }
        player.update();
    }
}
