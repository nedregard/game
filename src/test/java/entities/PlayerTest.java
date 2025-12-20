package entities;

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
        assertEquals(player.getPosition()[0], 384);
        player.applyLeftMovement();
        assertEquals(player.getPosition()[0], 383);
    }

    @Test
    void moveRight() {
        assertEquals(player.getPosition()[0], 384);
        player.applyRightMovement();
        assertEquals(player.getPosition()[0], 385);
    }

    @Test
    void moveDown() {
        assertEquals(player.getPosition()[1], 284);
        player.applyDownMovement();
        assertEquals(player.getPosition()[1], 285);
    }

    @Test
    void moveUp() {
        assertEquals(player.getPosition()[1], 284);
        player.applyUpMovement();
        assertEquals(player.getPosition()[1], 283);
    }
}