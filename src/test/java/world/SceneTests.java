package world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import graphics.sprites.SpriteHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import world.tiles.Tile;
import world.tiles.TileObject;
import world.tiles.TileType;

public class SceneTests {

    @Mock
    Graphics2D g2;

    @Test
    public void sceneInitializesWithCorrectDimensions() {
        Scene scene = new Scene("test", 50, 30);

        assertNotNull(scene.getTile(0, 0));
        assertNotNull(scene.getTile(49, 29));
    }

    @Test
    public void setTileOverridesTile() {
        Scene scene = new Scene("test", 10, 10);

        scene.setTile(5, 5, TileType.WATER, TileObject.STONEBLOCK);

        Tile tile = scene.getTile(5, 5);

        assertEquals(TileType.WATER, tile.getGround());
        assertEquals(TileObject.STONEBLOCK, tile.getObject());
    }

    @Test
    public void drawAllTiles() {
        MockitoAnnotations.initMocks(this);
        Tile[][] tiles = new Tile[2][2];
        Tile tile1 = mock(Tile.class);
        Tile tile2 = mock(Tile.class);
        Tile tile3 = mock(Tile.class);
        Tile tile4 = mock(Tile.class);
        tiles[0][0] = tile1;
        tiles[1][0] = tile2;
        tiles[0][1] = tile3;
        tiles[1][1] = tile4;

        Scene scene = new Scene("test", 2, 2);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                scene.setTile(i, j, tiles[i][j]);
            }
        }

        scene.draw(g2);

        verify(tile1, times(1)).draw(any(), anyInt(), anyInt());
        verify(tile2, times(1)).draw(any(), anyInt(), anyInt());
        verify(tile3, times(1)).draw(any(), anyInt(), anyInt());
        verify(tile4, times(1)).draw(any(), anyInt(), anyInt());
    }


    private Scene getWalkableTestScene() {
        Scene scene = new Scene("test", 10, 10);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                scene.setTile(x, y, TileType.GRASS, null);
            }
        }

        scene.setTile(3, 4, TileType.WATER, null);
        return scene;
    }

    @Test
    public void walkablePixelOnGrassReturnsTrue() {
        Scene scene = getWalkableTestScene();
        int pixelX = 2 * 32 + 5;
        int pixelY = 1 * 32 + 10;
        assertTrue(scene.isWalkablePixel(pixelX, pixelY));
    }

    @Test
    public void walkablePixelOnWaterReturnsFalse() {
        Scene scene = getWalkableTestScene();
        int pixelX = 3 * 32 + 1;
        int pixelY = 4 * 32 + 1;
        assertFalse(scene.isWalkablePixel(pixelX, pixelY));
    }

    @Test
    public void negativePixelXReturnsFalse() {
        Scene scene = getWalkableTestScene();
        assertFalse(scene.isWalkablePixel(-1, 50));
    }

    @Test
    public void negativePixelYReturnsFalse() {
        Scene scene = getWalkableTestScene();
        assertFalse(scene.isWalkablePixel(50, -1));
    }

    @Test
    public void pixelBeyondWorldWidthReturnsFalse() {
        Scene scene = getWalkableTestScene();
        int pixelX = 10 * 32; // exactly outside
        int pixelY = 5 * 32;
        assertFalse(scene.isWalkablePixel(pixelX, pixelY));
    }

    @Test
    public void pixelBeyondWorldHeightReturnsFalse() {
        Scene scene = getWalkableTestScene();
        int pixelX = 5 * 32;
        int pixelY = 10 * 32;
        assertFalse(scene.isWalkablePixel(pixelX, pixelY));
    }
}
