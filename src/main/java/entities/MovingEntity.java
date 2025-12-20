package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MovingEntity {
    private final int width;
    private final int height;
    private final int maxSpeed;
    private BufferedImage[] sprites = {null, null, null};
    private int[] position = {0, 0};
    private int[] speeds = {0, 0};
    private int spriteIndex;
    private int angle;

    public MovingEntity(int width, int height, int maxSpeed, String spriteFile) {
        this.width = width;
        this.height = height;
        this.maxSpeed = maxSpeed;
        loadSprites(spriteFile);
    }

    private void loadSprites(String spriteFile) {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResource("/graphics/sprites/" + spriteFile));
            for (var i = 0; i < 3; i++) {
                sprites[i] = getSprite(spriteSheet, i * width, 0);
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            // Optionally, create a fallback image
            for (int i = 0; i < 3; i++) {
                sprites[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }
        }
    }

    protected void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    private BufferedImage getSprite(BufferedImage spriteSheet, int x, int y) {
        return spriteSheet.getSubimage(x, y, width, height);
    }

    public void applyLeftMovement() {
        speeds[0] = -(Math.min(maxSpeed, Math.abs(speeds[0]) + 2));
        System.out.println("Player.applyLeftMovement, speed=" + speeds[0]);
    }

    public void applyRightMovement() {
        speeds[0] = (Math.min(maxSpeed, Math.abs(speeds[0]) + 2));
        System.out.println("Player.applyRightMovement, speed=" + speeds[0]);
    }

    public void applyUpMovement() {
        speeds[1] = -(Math.min(maxSpeed, Math.abs(speeds[1]) + 2));
        System.out.println("Player.applyUpMovement, speed=" + speeds[1]);
    }

    public void applyDownMovement() {
        speeds[1] = (Math.min(maxSpeed, Math.abs(speeds[1]) + 2));
        System.out.println("Player.applyDownMovement, speed=" + speeds[1]);
    }

    public void draw(Graphics2D g2) {
        angle = calculateAngle();
        g2.drawImage(rotateImageByDegrees(sprites[spriteIndex]), position[0], position[1], null);
    }

    private int calculateAngle() {
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

        return (int) (Math.round(angleDeg / 9.0) * 9) % 360;
    }

    private BufferedImage rotateImageByDegrees(BufferedImage img) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
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
                System.out.println(speed);
                moved = true;
                position[i] += speed;
                speeds[i] = speed + 1;
            }
        }
        if (moved) {
            spriteIndex = (spriteIndex + 1) % sprites.length;
        } else {
            spriteIndex = 0;
        }
    }
}
