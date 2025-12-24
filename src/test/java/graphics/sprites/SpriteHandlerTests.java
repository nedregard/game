package graphics.sprites;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

class SpriteHandlerTests {

    @Test
    public void constructorLoadsAndPrecomputesSprites() {
        SpriteHandler handler = new SpriteHandler(
            "test.png",
            32, 32,
            3,
            8
        );

        // Base sprites
        for (int i = 0; i < 3; i++) {
            assertNotNull(handler.getSprite(i, 0),
                "Base sprite " + i + " should be loaded");
        }

        // Rotated sprites
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 8; j++) {
                assertNotNull(handler.getSprite(i, j),
                    "Rotated sprite " + i + "," + j + " should be precomputed");
            }
        }
    }

    @Test
    public void rotatedSpriteHasDifferentDimensions() {
        SpriteHandler handler = new SpriteHandler(
            "test.png",
            32, 32,
            1,
            8
        );

        BufferedImage base = handler.getSprite(0, 0);
        BufferedImage rotated = handler.getSprite(0, 1);

        assertNotNull(rotated);
        assertTrue(
            rotated.getWidth() >= base.getWidth(),
            "Rotated sprite width should be >= base width"
        );
        assertTrue(
            rotated.getHeight() >= base.getHeight(),
            "Rotated sprite height should be >= base height"
        );
    }

    @Test
    public void getSpriteReturnsSameInstance() {
        SpriteHandler handler = new SpriteHandler(
            "test.png",
            32, 32,
            1,
            8
        );

        BufferedImage a = handler.getSprite(0, 3);
        BufferedImage b = handler.getSprite(0, 3);

        assertSame(a, b, "Sprites should be cached and immutable");
    }

    @Test
    public void invalidSpriteIndexThrowsException() {
        SpriteHandler handler = new SpriteHandler(
            "test.png",
            32, 32,
            1,
            8
        );

        assertThrows(
            ArrayIndexOutOfBoundsException.class,
            () -> handler.getSprite(5, 0)
        );
    }

    @Test
    public void invalidAngleIndexThrowsException() {
        SpriteHandler handler = new SpriteHandler(
            "test.png",
            32, 32,
            1,
            8
        );

        assertThrows(
            ArrayIndexOutOfBoundsException.class,
            () -> handler.getSprite(0, 10)
        );
    }

    @Test
    public void missingSpriteFileThrowsIllegalStateException() {
        assertThrows(
            IllegalStateException.class,
            () -> new SpriteHandler(
                "does_not_exist.png",
                32, 32,
                1,
                8
            )
        );
    }
}
