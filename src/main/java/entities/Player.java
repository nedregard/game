package entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import world.WorldHandler;

public class Player extends MovingEntity {
    private int health;
    private List<Projectile> projectiles = new ArrayList<>();

    public Player(WorldHandler world) {
        super(32, 32, 10, 3, 8, world);
        this.health = 100;
    }

    public void spawn(int x, int y) {
        setPosition(x, y);
    }

    public void update() {
        super.update();
        updateProjectiles();
    }

    public void fire() {
        projectiles.add(new Projectile(position[0], position[1], angleIndex, 10));
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        for (Projectile projectile : projectiles) {
            projectile.draw(g2);
        }
        g2.setColor(Color.RED);
        g2.drawRect(position[0], position[1], 32, 32);
    }

    private void updateProjectiles() {
        List<Projectile> tempProjectiles = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            projectile.update();
            if (!projectile.toDestroy()) {
                tempProjectiles.add(projectile);
            }
        }
        projectiles = tempProjectiles;
    }
}
