package graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class SpriteHandler {
    BufferedImage[][] sprites;
    private final int width;
    private final int height;
    private final int spriteCount;
    private final int angleCount;

    public SpriteHandler(int width, int height, int spriteCount, int angleCount) {
        this.width = width;
        this.height = height;
        this.spriteCount = spriteCount;
        this.angleCount = angleCount;
        sprites = new BufferedImage[spriteCount][angleCount];
    }

    public void loadSprites(String spriteFile) {
        URL url = getClass().getResource("/graphics/sprites/" + spriteFile);
        if (url == null) {
            throw new IllegalStateException("Sprite not found: " + spriteFile);
        }
        try {
            BufferedImage spriteSheet = ImageIO.read(url);
            for (var i = 0; i < spriteCount; i++) {
                BufferedImage sprite = spriteSheet.getSubimage(i * width, 0, width, height);
                sprites[i][0] = sprite;
            }
            rotateSprites();
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    public void rotateSprites() {
        for (int i = 0; i < spriteCount; i++) {
            for (int j = 1; j < angleCount; j++) {
                int angle = j * (360 / angleCount);
                sprites[i][j] = rotate(sprites[i][0], angle);
            }
        }
    }

    private BufferedImage getSprite(BufferedImage spriteSheet, int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }

    public BufferedImage rotate(BufferedImage sprite, int angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = sprite.getWidth();
        int h = sprite.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = rotated.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(sprite, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    public BufferedImage getSprite(int spriteIdx, int angleIdx) {
        BufferedImage sprite = sprites[spriteIdx][angleIdx];
        return sprite;
    }
}
