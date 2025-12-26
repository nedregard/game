package controls;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.KeyEvent;

import graphics.GamePanel;
import org.junit.jupiter.api.Test;

public class KeyHandlerTests {

    KeyHandler keyHandler = new KeyHandler();
    GamePanel gamePanel = new GamePanel();

    @Test
    public void testKeyPressedReleased() {
        assertFalse(keyHandler.upPressed);
        keyHandler.keyPressed(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_W));
        assertTrue(keyHandler.upPressed);
        keyHandler.keyReleased(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_W));
        assertFalse(keyHandler.upPressed);

        assertFalse(keyHandler.downPressed);
        keyHandler.keyPressed(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_S));
        assertTrue(keyHandler.downPressed);
        keyHandler.keyReleased(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_S));
        assertFalse(keyHandler.downPressed);

        assertFalse(keyHandler.leftPressed);
        keyHandler.keyPressed(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_A));
        assertTrue(keyHandler.leftPressed);
        keyHandler.keyReleased(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_A));
        assertFalse(keyHandler.leftPressed);

        assertFalse(keyHandler.rightPressed);
        keyHandler.keyPressed(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_D));
        assertTrue(keyHandler.rightPressed);
        keyHandler.keyReleased(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_D));
        assertFalse(keyHandler.rightPressed);

        assertFalse(keyHandler.firePressed);
        keyHandler.keyPressed(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_SPACE));
        assertTrue(keyHandler.firePressed);
        keyHandler.keyReleased(new KeyEvent(gamePanel, 0, 0, 0, KeyEvent.VK_SPACE));
        assertFalse(keyHandler.firePressed);
    }
}
