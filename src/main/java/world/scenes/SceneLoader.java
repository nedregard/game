package world.scenes;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import world.tiles.TileObject;
import world.tiles.TileType;

public class SceneLoader {

    public static Scene load(String file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SceneData data = mapper.readValue(
            SceneLoader.class.getResource(file),
            SceneData.class
        );

        Scene scene = new Scene(data.name, data.width, data.height);

        for (int x = 0; x < data.width; x++) {
            for (int y = 0; y < data.height; y++) {
                TileData t = data.tiles[x][y];
                scene.setTile(
                    x, y,
                    TileType.valueOf(t.ground),
                    t.object == null ? null : TileObject.valueOf(t.object)
                );
            }
        }

        for (LightData l : data.lights) {
            //scene.addLight(new Light(l.x, l.y, l.radius, l.intensity));
        }

        return scene;
    }
}
