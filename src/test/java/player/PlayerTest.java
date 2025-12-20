package player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setup() {
        player = new Player();
        player.spawn(400 - 16, 300 - 16);
    }

    @Test
    void moveLeft() {
        assertEquals(player.getPostion()[0], 384);
        player.moveLeft();
        assertEquals(player.getPostion()[0], 383);
    }

    @Test
    void moveRight() {
        assertEquals(player.getPostion()[0], 384);
        player.moveRight();
        assertEquals(player.getPostion()[0], 385);
    }

    @Test
    void moveDown() {
        assertEquals(player.getPostion()[1], 284);
        player.moveDown();
        assertEquals(player.getPostion()[1], 285);
    }

    @Test
    void moveUp() {
        assertEquals(player.getPostion()[1], 284);
        player.moveUp();
        assertEquals(player.getPostion()[1], 283);
    }
}