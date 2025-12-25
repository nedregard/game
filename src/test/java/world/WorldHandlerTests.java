package world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldHandlerTests {

    @Test
    void testSetAndGetScene() {
        WorldHandler handler = new WorldHandler();
        assertNull(handler.getScene(), "Initially, scene should be null");

        Scene scene = new Scene("test", 10, 10);
        handler.setScene(scene);

        assertNotNull(handler.getScene(), "Scene should no longer be null");
        assertEquals(scene, handler.getScene(), "Getter should return the same Scene object");
    }

    @Test
    void testOverwriteScene() {
        WorldHandler handler = new WorldHandler();
        Scene scene1 = new Scene("scene1", 5, 5);
        Scene scene2 = new Scene("scene2", 7, 7);

        handler.setScene(scene1);
        assertEquals(scene1, handler.getScene());

        handler.setScene(scene2);
        assertEquals(scene2, handler.getScene(), "Setter should overwrite previous scene");
    }
}
