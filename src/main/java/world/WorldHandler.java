package world;

import world.scenes.Scene;

public class WorldHandler {

    private Scene scene;
    public WorldHandler() {

    }

    public boolean isWalkablePixel(int pixelX, int pixelY) {
        return scene.isWalkablePixel(pixelX, pixelY);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }
}
