package entities;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import graphics.sprites.SpriteHandler;
import graphics.sprites.SpriteManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import world.WorldHandler;

public class MovingEntityTests {

    private MockedStatic<SpriteManager> spriteManagerStatic;
    private SpriteHandler spriteHandler;
    private WorldHandler world;
    private Graphics2D g2;

    @BeforeEach
    void setup() {
        spriteHandler = mock(SpriteHandler.class);
        world = mock(WorldHandler.class);
        g2 = mock(Graphics2D.class);

        when(world.isWalkablePixel(anyInt(), anyInt())).thenReturn(true);

        spriteManagerStatic = mockStatic(SpriteManager.class);
        spriteManagerStatic.when(() ->
            SpriteManager.get(
                eq("player"),
                eq("player.png"),
                anyInt(), anyInt(),
                anyInt(), anyInt()
            )
        ).thenReturn(spriteHandler);

        when(spriteHandler.getSprite(anyInt(), anyInt()))
            .thenReturn(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
    }

    private static class TestMovingEntity extends MovingEntity {
        public TestMovingEntity(int width, int height, int maxSpeed, int spriteCount, int angleCount, WorldHandler world) {
            super(width, height, maxSpeed, spriteCount, angleCount, world);
        }

        public void setPos(int x, int y) {
            super.setPosition(x, y);
        }

        public void setSpeeds(int vx, int vy) {
            this.speeds[0] = vx;
            this.speeds[1] = vy;
        }

        public int calcAngle() {
            return super.calculateAngle();
        }
    }

    @AfterEach
    void tearDown() {
        if (spriteManagerStatic != null) spriteManagerStatic.close();
    }

    @Test
    void applyRightMovement_acceleratesAndCapsAtMaxSpeed() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);

        e.applyRightMovement();
        assertEquals(2, e.speeds[0]);

        e.applyRightMovement();
        assertEquals(4, e.speeds[0]);

        e.applyRightMovement();
        assertEquals(5, e.speeds[0]);

        e.applyRightMovement();
        assertEquals(5, e.speeds[0]);
    }

    @Test
    void applyLeftMovement_acceleratesNegativeAndCapsAtMaxSpeed() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);

        e.applyLeftMovement();
        assertEquals(-2, e.speeds[0]);

        e.applyLeftMovement();
        assertEquals(-4, e.speeds[0]);

        e.applyLeftMovement();
        assertEquals(-5, e.speeds[0]);

        e.applyLeftMovement();
        assertEquals(-5, e.speeds[0]);
    }

    @Test
    void applyUpMovement_acceleratesNegativeAndCapsAtMaxSpeed() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);

        e.applyUpMovement();
        assertEquals(-2, e.speeds[1]);

        e.applyUpMovement();
        assertEquals(-4, e.speeds[1]);

        e.applyUpMovement();
        assertEquals(-5, e.speeds[1]);

        e.applyUpMovement();
        assertEquals(-5, e.speeds[1]);
    }

    @Test
    void applyDownMovement_acceleratesAndCapsAtMaxSpeed() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);

        e.applyDownMovement();
        assertEquals(2, e.speeds[1]);

        e.applyDownMovement();
        assertEquals(4, e.speeds[1]);

        e.applyDownMovement();
        assertEquals(5, e.speeds[1]);

        e.applyDownMovement();
        assertEquals(5, e.speeds[1]);
    }

    @Test
    void update_movesWhenWalkable_andAppliesFriction() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);
        e.setPos(100, 100);
        e.setSpeeds(3, 3);

        e.update();

        assertArrayEquals(new int[]{103, 103}, e.getPosition());

        assertEquals(2, e.speeds[0]);
        assertEquals(2, e.speeds[1]);
    }

    @Test
    void update_doesNotMoveWhenBlocked_butStillAppliesFriction() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);
        e.setPos(100, 100);
        e.setSpeeds(3, 0);

        when(world.isWalkablePixel(anyInt(), anyInt())).thenReturn(false);

        e.update();

        assertArrayEquals(new int[]{100, 100}, e.getPosition());

        assertEquals(2, e.speeds[0]);
    }

    @Test
    void update_spriteIndexAdvancesOnlyWhenMoved_otherwiseResetsToZero() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);
        e.setPos(0, 0);

        e.setSpeeds(2, 0);
        e.update();
        int spriteIndexAfterMove1 = getPrivateInt(e, "spriteIndex");
        assertEquals(1, spriteIndexAfterMove1);

        e.update();
        int spriteIndexAfterMove2 = getPrivateInt(e, "spriteIndex");
        assertEquals(2, spriteIndexAfterMove2);

        e.update();
        int spriteIndexAfterStop = getPrivateInt(e, "spriteIndex");
        assertEquals(0, spriteIndexAfterStop);
    }

    @Test
    void calculateAngle_keepsPreviousAngleWhenNotMoving() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);

        e.angle = 180;

        e.setSpeeds(0, 0);
        assertEquals(180, e.calcAngle());
    }

    @Test
    void calculateAngle_quantizesToAngleCountSteps_with90DegreeOffset() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);

        e.setSpeeds(1, 0);
        assertEquals(90, e.calcAngle());

        e.setSpeeds(0, 1);
        assertEquals(180, e.calcAngle());

        e.setSpeeds(-1, 0);
        assertEquals(270, e.calcAngle());

        e.setSpeeds(0, -1);
        assertEquals(0, e.calcAngle());
    }

    @Test
    void draw_callsSpriteHandlerAndDrawsAtPosition_withCorrectAngleIndex() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);
        e.setPos(50, 60);

        e.setSpeeds(2, 0);
        e.update();

        e.setSpeeds(0, 1);

        Image img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        when(spriteHandler.getSprite(eq(1), eq(4))).thenReturn((BufferedImage) img);

        e.draw(g2);

        verify(spriteHandler).getSprite(1, 4);
        verify(g2).drawImage(eq((BufferedImage)img), eq(52), eq(60), isNull());
    }

    @Test
    void update_checksFourCornersForCollision() {
        TestMovingEntity e = new TestMovingEntity(10, 10, 5, 4, 8, world);
        e.setPos(100, 200);
        e.setSpeeds(3, 0);

        e.update();

        int nextX = 103;
        int nextY = 200;
        int left = nextX;
        int right = nextX + 10 - 1;
        int top = nextY;
        int bottom = nextY + 10 - 1;

        verify(world).isWalkablePixel(left, bottom);
        verify(world).isWalkablePixel(right, bottom);
        verify(world).isWalkablePixel(left, top);
        verify(world).isWalkablePixel(right, top);
    }

    private static int getPrivateInt(Object target, String fieldName) {
        try {
            Field f = target.getClass().getSuperclass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (int) f.get(target);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read private field: " + fieldName, e);
        }
    }
}
