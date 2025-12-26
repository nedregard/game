package world.scenes;

import java.awt.Point;
import java.util.List;

public class SceneData {
    public String name;
    public int tileSize;
    public int width;
    public int height;
    public TileData[][] tiles;
    public List<LightData> lights;
    public Point playerSpawn;
}
