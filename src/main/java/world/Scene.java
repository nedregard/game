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

    private void loadTiles(String name) {
        //load from text file
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 20) {
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
