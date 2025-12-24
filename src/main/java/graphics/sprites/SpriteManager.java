package graphics.sprites;

import java.util.HashMap;
import java.util.Map;

public final class SpriteManager {

    private static final Map<String, SpriteHandler> CACHE = new HashMap<>();

    public static SpriteHandler get(String key,
                                    String file,
                                    int w, int h,
                                     int sprites, int angles) {
        return CACHE.computeIfAbsent(key,
            k -> new SpriteHandler(file, w, h, sprites, angles));
    }
}
