package world.tiles;

import java.awt.*;

import graphics.sprites.SpriteHandler;
import graphics.sprites.SpriteManager;

public class Tile {
    private final TileType ground;
    private final TileObject object;
    private final SpriteHandler groundSprite;
    private final SpriteHandler objectSprite;
    public Tile(TileType ground, TileObject object) {
        this.ground = ground;
        this.object = object;
        this.groundSprite = SpriteManager.get(
            ground.name(),
            ground.name() + ".png",
            32, 32, 1, 1
        );

        if (object != null) {
            this.objectSprite = SpriteManager.get(
                object.name(),
                object.name() + ".png",
                32, 32, 1, 1
            );
        } else {
            this.objectSprite = null;
        }
    }

    public boolean isWalkable() {
        if (object != null) {
            return object.isWalkable();
        }
        return ground.isWalkable();
    }

    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(groundSprite.getSprite(0, 0), x, y, null);
        if (objectSprite != null) {
            g2.drawImage(objectSprite.getSprite(0, 0), x, y, null);
        }
        g2.setColor(Color.BLUE);
        g2.drawRect(x, y, 32, 32);
    }

    public TileType getGround() {
        return ground;
    }

    public TileObject getObject() {
        return object;
    }
}
