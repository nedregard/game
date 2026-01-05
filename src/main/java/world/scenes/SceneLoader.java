package world.scenes;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import world.tiles.TileObject;
import world.tiles.TileType;

public class SceneLoader {

    public static Scene load(String file) throws IOException {
        URL url = SceneLoader.class.getResource("/scenes/" + file + ".json");
        if (url == null) {
            throw new IllegalStateException("Scene not found: " + file + ".json");
        }

        ObjectMapper mapper = new ObjectMapper();
        SceneData data = mapper.readValue(
            url,
            SceneData.class
        );

        Scene scene = new Scene(data.name, data.width, data.height);

        for (int x = 0; x < data.height; x++) {
            for (int y = 0; y < data.width; y++) {
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
