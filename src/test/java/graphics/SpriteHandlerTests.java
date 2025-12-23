package graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpriteHandlerTests {
    SpriteHandler handler;
    BufferedImage baseSprite;

    @BeforeEach
    void setUp() {
        handler = new SpriteHandler(32, 32, 1, 8);
        baseSprite = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        handler.sprites[0][0] = baseSprite;
        handler.rotateSprites();
    }

    @Test
    void testBaseSpriteLoaded() {
        assertNotNull(handler.sprites[0][0], "Base sprite should exist");
        assertEquals(32, handler.sprites[0][0].getWidth());
        assertEquals(32, handler.sprites[0][0].getHeight());
    }

    @Test
    void testRotationCreatesNewSprite() {
        BufferedImage rotated = handler.getSprite(0, 1);
        assertNotNull(rotated);
        assertNotEquals(baseSprite, rotated);
    }

    @Test
    void testRotationIsCached() {
        BufferedImage first = handler.getSprite(0, 1);
        BufferedImage second = handler.getSprite(0, 1);
        assertEquals(first, second, "Rotated sprite should be cached");
    }

    @Test
    void testRotationDimensionsChange() {
        BufferedImage rotated = handler.getSprite(0, 1);
        assertTrue(rotated.getWidth() > 32);
        assertTrue(rotated.getHeight() > 32);
    }

    @Test
    void testZeroRotationReturnsSameSize() {
        BufferedImage rotated = handler.getSprite(0, 0);
        assertEquals(32, rotated.getWidth());
        assertEquals(32, rotated.getHeight());
    }

    @Test
    void testInvalidSpriteIndexThrows() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            handler.getSprite(5, 0)
        );
    }

    @Test
    void testInvalidAngleIndexThrows() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            handler.getSprite(0, 10)
        );
    }
}
