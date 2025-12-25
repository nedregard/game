package world.tiles;

import java.awt.Color;

public enum TileType {
    GRASS(true, false, Color.GREEN),
    WATER(false, true, Color.BLUE),
    SAND(true, false, Color.YELLOW);

    public final boolean walkable;
    public final boolean blocksProjectiles;
    public final Color debugColor;

    TileType(boolean walkable, boolean blocksProjectiles, Color debugColor) {
        this.walkable = walkable;
        this.blocksProjectiles = blocksProjectiles;
        this.debugColor = debugColor;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
