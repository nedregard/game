package entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.awt.Graphics2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProjectileTests {

    @Mock
    Graphics2D g2;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void projectileMovesUp() {
        Projectile p = new Projectile(100, 100, 0, 5);

        p.update();

        assertEquals(100, pPositionX(p));
        assertEquals(95, pPositionY(p));
        assertFalse(p.toDestroy());
    }

    @Test
    public void projectileMovesRight() {
        Projectile p = new Projectile(100, 100, 2, 5);

        p.update();

        assertEquals(105, pPositionX(p));
        assertEquals(100, pPositionY(p));
    }

    @Test
    public void projectileMovesDown() {
        Projectile p = new Projectile(100, 100, 4, 5);

        p.update();

        assertEquals(100, pPositionX(p));
        assertEquals(105, pPositionY(p));
    }

    @Test
    public void projectileMovesLeft() {
        Projectile p = new Projectile(100, 100, 6, 5);

        p.update();

        assertEquals(95, pPositionX(p));
        assertEquals(100, pPositionY(p));
    }

    @Test
    public void diagonalMovementUpRight() {
        Projectile p = new Projectile(100, 100, 1, 5);

        p.update();

        assertEquals(105, pPositionX(p));
        assertEquals(95, pPositionY(p));
    }

    @Test
    public void projectileDestroyedWhenLeavingBoundsX() {
        Projectile p = new Projectile(799, 100, 2, 5);

        p.update();

        assertTrue(p.toDestroy());
    }

    @Test
    public void projectileDestroyedWhenLeavingBoundsY() {
        Projectile p = new Projectile(100, 599, 4, 5);

        p.update();

        assertTrue(p.toDestroy());
    }

    @Test
    public void projectileNotDestroyedInsideBounds() {
        Projectile p = new Projectile(400, 300, 3, 5);

        p.update();

        assertFalse(p.toDestroy());
    }

    @Test
    public void drawSprite() {
        Projectile p = new Projectile(400, 300, 3, 5);

        p.draw(g2);

        Mockito.verify(g2, Mockito.times(1))
            .drawImage(Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any());
    }

    private int pPositionX(Projectile p) {
        try {
            var f = Projectile.class.getDeclaredField("position");
            f.setAccessible(true);
            int[] pos = (int[]) f.get(p);
            return pos[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int pPositionY(Projectile p) {
        try {
            var f = Projectile.class.getDeclaredField("position");
            f.setAccessible(true);
            int[] pos = (int[]) f.get(p);
            return pos[1];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
