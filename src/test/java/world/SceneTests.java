package world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}
