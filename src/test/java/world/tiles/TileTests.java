package world.tiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.sprites.SpriteHandler;
import graphics.sprites.SpriteManager;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class TileTests {

    @Test
    public void walkableWhenGroundIsWalkableAndNoObject() {
        Tile tile = new Tile(TileType.GRASS, null);
        assertTrue(tile.isWalkable());
    }

    @Test
    public void notWalkableWhenGroundIsNotWalkable() {
        Tile tile = new Tile(TileType.WATER, null);
        assertFalse(tile.isWalkable());
    }

    @Test
    public void objectOverridesGroundWalkability() {
        Tile tile = new Tile(TileType.GRASS, TileObject.STONEBLOCK);
        assertFalse(tile.isWalkable());
    }

    @Test
    public void drawDrawsGroundAndObject() {
        SpriteHandler groundHandler = mock(SpriteHandler.class);
        SpriteHandler objectHandler = mock(SpriteHandler.class);

        BufferedImage dummy = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        when(groundHandler.getSprite(0, 0)).thenReturn(dummy);
        when(objectHandler.getSprite(0, 0)).thenReturn(dummy);

        try (MockedStatic<SpriteManager> mocked = mockStatic(SpriteManager.class)) {
            mocked.when(() ->
                SpriteManager.get(eq("GRASS"), any(), anyInt(), anyInt(), anyInt(), anyInt())
            ).thenReturn(groundHandler);

            mocked.when(() ->
                SpriteManager.get(eq("STONEBLOCK"), any(), anyInt(), anyInt(), anyInt(), anyInt())
            ).thenReturn(objectHandler);

            Tile tile = new Tile(TileType.GRASS, TileObject.STONEBLOCK);
            Graphics2D g2 = mock(Graphics2D.class);

            tile.draw(g2, 64, 96);

            verify(g2, times(2))
                .drawImage(eq(dummy), eq(64), eq(96), isNull());
        }
    }
}
