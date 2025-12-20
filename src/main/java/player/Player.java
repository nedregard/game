package player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {
    private int x;
    private int y;
    private int health;
    private int spriteIndex;
    private BufferedImage[] sprites;

    public Player() {
        this.health = 100;
        this.spriteIndex = 0;
        sprites = new BufferedImage[3];
        //load sprites here
        for (int i = 0; i < 3; i++) {
            sprites[i] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = sprites[i].createGraphics();
            g2.setColor(new Color(100 + i * 50, 100, 200));
            g2.fillRect(0, 0, 32, 32);
            g2.dispose();
        }
    }

    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        System.out.println("Player.moveLeft");
        x = Math.max(0, x - 1);
        spriteIndex = (spriteIndex + 1) % sprites.length;
        System.out.println("Player moved left to x=" + x + " y=" + y);
    }

    public void moveRight() {
        System.out.println("Player.moveRight");
        x = Math.min(800, x + 1);
        spriteIndex = (spriteIndex + 1) % sprites.length;
        System.out.println("Player moved right to x=" + x + " y=" + y);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(sprites[spriteIndex], x, y, null);
    }
}
