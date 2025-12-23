package entities;

import java.awt.*;

import graphics.SpriteHandler;

public class MovingEntity {
    private final int maxSpeed;
    private final int angleCount;
    private final int spriteCount;
    private int[] position = {0, 0};
    protected int[] speeds = {0, 0};
    protected int angle;
    private int spriteIndex;
    private SpriteHandler spriteHandler;

    public MovingEntity(int width, int height, int maxSpeed, String spriteFile, int spriteCount, int angleCount) {
        this.maxSpeed = maxSpeed;
        this.angleCount = angleCount;
        this.spriteCount = spriteCount;
        spriteHandler = new SpriteHandler(width, height, spriteCount, angleCount);
        spriteHandler.loadSprites(spriteFile);
    }

    protected void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public void applyLeftMovement() {
        speeds[0] = -(Math.min(maxSpeed, Math.abs(speeds[0]) + 2));
        //System.out.println("Player.applyLeftMovement, speed=" + speeds[0]);
    }

    public void applyRightMovement() {
        speeds[0] = (Math.min(maxSpeed, Math.abs(speeds[0]) + 2));
        //System.out.println("Player.applyRightMovement, speed=" + speeds[0]);
    }

    public void applyUpMovement() {
        speeds[1] = -(Math.min(maxSpeed, Math.abs(speeds[1]) + 2));
        //System.out.println("Player.applyUpMovement, speed=" + speeds[1]);
    }

    public void applyDownMovement() {
        speeds[1] = (Math.min(maxSpeed, Math.abs(speeds[1]) + 2));
        //System.out.println("Player.applyDownMovement, speed=" + speeds[1]);
    }

    public void draw(Graphics2D g2) {
        angle = calculateAngle();
        int angleIndex = Math.floorMod(angle, 360) / (360 / angleCount);
        g2.drawImage(spriteHandler.getSprite(spriteIndex, angleIndex), position[0], position[1], null);
    }

    //todo: AngleHandler
    protected int calculateAngle() {
        int vx = speeds[0];
        int vy = speeds[1];
        if (vx == 0 && vy == 0) {
            return angle;
        }
        double angleRad = Math.atan2(vy, vx);
        double angleDeg = Math.toDegrees(angleRad);
        angleDeg += 90;
        if (angleDeg < 0) {
            angleDeg += 360;
        }

        return (int) (Math.round(angleDeg / (360 / angleCount)) * (360 / angleCount)) % 360;
    }

    public int[] getPosition() {
        return position;
    }

    public void update() {
        boolean moved = false;
        for (int i = 0; i < speeds.length; i++) {
            int speed = speeds[i];
            if (speed > 0) {
                moved = true;
                position[i] += speed;
                speeds[i] = speed - 1;
            } else if (speed < 0) {
                moved = true;
                position[i] += speed;
                speeds[i] = speed + 1;
            }
        }

        if (moved) {
            spriteIndex = (spriteIndex + 1) % spriteCount;
        } else {
            this.spriteIndex = 0;
        }
    }
}
