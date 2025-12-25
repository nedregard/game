package world.tiles;

public enum TileObject {
    STONEBLOCK(false);

    private final boolean walkable;

    TileObject(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }
}