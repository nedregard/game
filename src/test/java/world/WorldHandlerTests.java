package world;

import org.junit.jupiter.api.Test;
import world.scenes.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WorldHandlerTests {

    @Test
    public void testSetAndGetScene() {
        WorldHandler handler = new WorldHandler();
        assertNull(handler.getScene(), "Initially, scene should be null");

        Scene scene = new Scene("test", 10, 10);
        handler.setScene(scene);

        assertNotNull(handler.getScene(), "Scene should no longer be null");
        assertEquals(scene, handler.getScene(), "Getter should return the same Scene object");
    }

    @Test
    public void testOverwriteScene() {
        WorldHandler handler = new WorldHandler();
        Scene scene1 = new Scene("scene1", 5, 5);
        Scene scene2 = new Scene("scene2", 7, 7);

        handler.setScene(scene1);
        assertEquals(scene1, handler.getScene());

        handler.setScene(scene2);
        assertEquals(scene2, handler.getScene(), "Setter should overwrite previous scene");
    }

    @Test
    public void testIsWalkablePixel() {
        WorldHandler handler = new WorldHandler();
        Scene scene = mock(Scene.class);

        handler.setScene(scene);
        handler.isWalkablePixel(0, 0);
        verify(scene, times(1)).isWalkablePixel(0,0);
    }
}
