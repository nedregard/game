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
}