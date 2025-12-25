package entities;

import java.awt.*;

import graphics.sprites.SpriteHandler;
import graphics.sprites.SpriteManager;
import world.WorldHandler;

public class MovingEntity {
    private final int width;
    private final int height;
    private final int maxSpeed;
    private final int angleCount;
    private final int spriteCount;
    protected int[] position = {0, 0};
    protected int[] speeds = {0, 0};
    protected int angle;
    protected int angleIndex;
    private int spriteIndex;
    private SpriteHandler spriteHandler;
    private final WorldHandler world;

    public MovingEntity(int width, int height, int maxSpeed, int spriteCount, int angleCount, WorldHandler world) {
        this.width = width;
        this.height = height;
        this.maxSpeed = maxSpeed;
        this.angleCount = angleCount;
        this.spriteCount = spriteCount;
        this.world = world;
        spriteHandler = SpriteManager.get(
            "player",
            "player.png",
            width, height,
            spriteCount, angleCount
        );
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
        angleIndex = Math.floorMod(angle, 360) / (360 / angleCount);
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

        if (speeds[0] != 0) {
            int nextX = position[0] + speeds[0];

            if (canMoveTo(nextX, position[1])) {
                position[0] = nextX;
                moved = true;
            }

            speeds[0] += (speeds[0] > 0) ? -1 : 1;
        }

        if (speeds[1] != 0) {
            int nextY = position[1] + speeds[1];

            if (canMoveTo(position[0], nextY)) {
                position[1] = nextY;
                moved = true;
            }

            speeds[1] += (speeds[1] > 0) ? -1 : 1;
        }

        spriteIndex = moved ? (spriteIndex + 1) % spriteCount : 0;
    }

    private boolean canMoveTo(int nextX, int nextY) {
        int left   = nextX;
        int right  = nextX + width - 1;
        int top    = nextY;
        int bottom = nextY + height - 1;
        return
            world.isWalkablePixel(left,  bottom) &&
            world.isWalkablePixel(right,  bottom) &&
            world.isWalkablePixel(left,  top) &&
            world.isWalkablePixel(right, top);
    }
}
