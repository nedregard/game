package entities;

import java.awt.Graphics2D;

import graphics.sprites.SpriteHandler;
import graphics.sprites.SpriteManager;

public class Projectile {
    private int[] position = {0, 0};
    private final int angleIndex;
    private final int speed;
    private SpriteHandler spriteHandler;
    boolean destroy;

    public Projectile(int x, int y, int angleIndex, int speed) {
        position[0] = x;
        position[1] = y;
        this.angleIndex = angleIndex;
        this.speed = speed;
        spriteHandler = SpriteManager.get(
            "projectile",
            "projectile.png",
            2, 2,
            1, 1
        );
    }

    public void update() {
        if (angleIndex == 5 || angleIndex == 6 || angleIndex == 7) {
            position[0] = position[0] - speed;
        }
        if (angleIndex == 1 || angleIndex == 2 || angleIndex == 3) {
            position[0] = position[0] + speed;
        }
        if (angleIndex == 0 || angleIndex == 1 || angleIndex == 7) {
            position[1] = position[1] - speed;
        }
        if (angleIndex == 3 || angleIndex == 4 || angleIndex == 5) {
            position[1] = position[1] + speed;
        }

        if ((position[0] < 0 || position[0] > 800) || (position[1] < 0 || position[1] > 600)) {
            destroy = true;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(spriteHandler.getSprite(0, 0), position[0], position[1], null);
    }

    public boolean toDestroy() {
        return destroy;
    }
}
