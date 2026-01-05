package random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import world.scenes.SceneData;
import world.scenes.TileData;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeGenerator {

    public static SceneData generateMazeScene(String name, int width, int height, int tileSize, long seed) {
        boolean[][] wall = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                wall[y][x] = true;
            }
        }

        Random rng = new Random(seed);

        int startX = 1;
        int startY = 1;

        startX = Math.min(startX, width - 2);
        startY = Math.min(startY, height - 2);

        wall[startY][startX] = false;

        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{startX, startY});

        int[][] dirs = new int[][]{
            {0, -2},
            {2, 0},
            {0, 2},
            {-2, 0}
        };

        while (!stack.isEmpty()) {
            int[] cur = stack.peek();
            int cx = cur[0], cy = cur[1];

            ArrayList<int[]> neighbors = new ArrayList<>();
            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];

                if (nx <= 0 || ny <= 0 || nx >= width - 1 || ny >= height - 1) continue;
                if (wall[ny][nx]) {
                    neighbors.add(new int[]{nx, ny});
                }
            }

            if (neighbors.isEmpty()) {
                stack.pop();
                continue;
            }

            Collections.shuffle(neighbors, rng);
            int[] next = neighbors.get(0);
            int nx = next[0], ny = next[1];

            int wx = (cx + nx) / 2;
            int wy = (cy + ny) / 2;

            wall[wy][wx] = false;
            wall[ny][nx] = false;

            stack.push(new int[]{nx, ny});
        }

        punchRandomHoles(wall, rng, width, height, 25);

        TileData[][] tiles = new TileData[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TileData t = new TileData();
                t.ground = "GRASS";
                t.object = wall[y][x] ? "STONEBLOCK" : null;
                tiles[y][x] = t;
            }
        }

        tiles[startY][startX].object = null;

        SceneData scene = new SceneData();
        scene.name = name;
        scene.width = width;
        scene.height = height;
        scene.tileSize = tileSize;
        scene.tiles = tiles;
        scene.lights = java.util.List.of();
        scene.playerSpawn = new Point(startX, startY);

        return scene;
    }

    private static void punchRandomHoles(boolean[][] wall, Random rng, int width, int height, int holes) {
        for (int i = 0; i < holes; i++) {
            int x = 1 + rng.nextInt(Math.max(1, width - 2));
            int y = 1 + rng.nextInt(Math.max(1, height - 2));

            if (x == 1 || y == 1 || x == width - 2 || y == height - 2) continue;
            wall[y][x] = false;
        }
    }

    public static void main(String[] args) throws Exception {
        SceneData scene = generateMazeScene("test", 32, 24, 32, 12345L);

        ObjectMapper om = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

        String json = om.writeValueAsString(scene);
        System.out.println(json);
    }
}
