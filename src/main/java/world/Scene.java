package world;

import java.awt.Graphics2D;

import world.tiles.Tile;
import world.tiles.TileObject;
import world.tiles.TileType;

public class Scene {
    private final int width;
    private final int height;

    private final Tile[][] tiles;
    public Scene(String name, int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        loadTiles(name);
    }

    public boolean isWalkablePixel(int pixelX, int pixelY) {
        if (pixelX < 0 || pixelY < 0)
            return false;

        int col = pixelX / 32;
        int row = pixelY / 32;

        if (col >= width || row >= height) {
            return false;
        }

        return tiles[col][row].isWalkable();
    }

    private void loadTiles(String name) {
        //load from text file
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 20 || i == 5 || j == 5 || j == 15 || (i == 10 && j == 10)) {
                    tiles[i][j] = new Tile(TileType.GRASS, TileObject.STONEBLOCK);
                } else {
                    tiles[i][j] = new Tile(TileType.GRASS, null);
                }
            }
        }
    }

    public void setTile(int col, int row, TileType type, TileObject object) {
        tiles[col][row] = new Tile(type, object);
    }

    public void setTile(int col, int row, Tile tile) {
        tiles[col][row] = tile;
    }

    public Tile getTile(int col, int row) {
        return tiles[col][row];
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j].draw(g2, i * 32, j * 32);
            }
        }
    }
}
