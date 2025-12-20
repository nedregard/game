package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MovingEntity {
    private int health;
    //private int spriteIndex;

    public Player() {
        super(32, 32, 5, "player.png");
        this.health = 100;
    }


    public void spawn(int x, int y) {
        setPosition(x, y);
    }

    public void update() {
        super.update();
    }
}
