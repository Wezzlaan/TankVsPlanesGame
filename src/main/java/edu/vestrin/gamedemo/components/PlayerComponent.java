package edu.vestrin.gamedemo.components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import edu.vestrin.gamedemo.util.ConfigUtil;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static java.lang.Math.clamp;

public class PlayerComponent extends Component {

    private LocalTimer shootTimer = newLocalTimer();
    private double shootDelay = 0.08;
    private int shotLevel = 1;

    private double moveSpeed = ConfigUtil.PLAYER_START_MOVESPEED;

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }

    public void onUpdate(double tpf) {
        double x = entity.getX();

        double minX = ConfigUtil.PLAY_ZONE_MIN_X - 50;
        double maxX = ConfigUtil.PLAY_ZONE_MAX_X - (entity.getWidth() + 80);

        entity.setX(Math.max(minX, Math.min(maxX, x)));
        entity.setY(Math.max(0, Math.min(getAppHeight() - entity.getHeight(), entity.getY())));

    }

    public void left() {
        entity.translateX(-moveSpeed * FXGL.tpf());
    }

    public void right() {
        entity.translateX(moveSpeed * FXGL.tpf());
    }

    public void boostSpeed() {
        double boostedSpd = ConfigUtil.PLAYER_BOOSTED_MOVESPEED;
        double duration = ConfigUtil.ENERGY_DURATION;
        moveSpeed += boostedSpd;

        getGameTimer().runOnceAfter(() -> {
            this.moveSpeed -= boostedSpd;
        }, Duration.seconds(duration));
    }

    public void shoot() {
        if (shootTimer.elapsed(Duration.seconds(shootDelay))) {
            FXGL.play("machinegun.wav");

            if (shotLevel == 1) {
                spawnBullet(0);
            } else if (shotLevel == 2) {
                spawnBullet(-25);
                spawnBullet(25);
            } else if (shotLevel == 3) {
                spawnBullet(-25);
                spawnBullet(0);
                spawnBullet(25);
            } else if (shotLevel == 4) {
                spawnBullet(-25);
                spawnBullet(-10);
                spawnBullet(10);
                spawnBullet(25);
            } else if (shotLevel == 5) {
                spawnBullet(-25);
                spawnBullet(-10);
                spawnBullet(0);
                spawnBullet(10);
                spawnBullet(25);
            } else if (shotLevel == 6) {
                spawnBullet(-35);
                spawnBullet(-25);
                spawnBullet(-10);
                spawnBullet(0);
                spawnBullet(10);
                spawnBullet(25);
            } else if (shotLevel == 7) {
                spawnBullet(-35);
                spawnBullet(-25);
                spawnBullet(-10);
                spawnBullet(0);
                spawnBullet(10);
                spawnBullet(25);
                spawnBullet(35);
            } else if (shotLevel == 8) {
                spawnBullet(-40);
                spawnBullet(-35);
                spawnBullet(-25);
                spawnBullet(-10);
                spawnBullet(0);
                spawnBullet(10);
                spawnBullet(25);
                spawnBullet(35);
            } else if (shotLevel == 9) {
                spawnBullet(-40);
                spawnBullet(-35);
                spawnBullet(-25);
                spawnBullet(-10);
                spawnBullet(0);
                spawnBullet(10);
                spawnBullet(25);
                spawnBullet(35);
                spawnBullet(40);
            }
            shootTimer.capture();
        }
    }

    private void spawnBullet(double offset) {
        Point2D center = entity.getCenter();

        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);

        Vec2 perpendicular = new Vec2(dir.y, -dir.x);

        Point2D spawnPoint = center.add(dir.x * 85, dir.y * 85)
                .add(perpendicular.x * offset, perpendicular.y * offset);

        spawn("bullet", new SpawnData(
                spawnPoint.subtract(15,0)
            ).put("dir", dir.toPoint2D()));
    }

    public void upgradeWeapon() {
        if (shotLevel < ConfigUtil.PLAYER_MAX_WEAPONS) {
            shotLevel++;
        }
    }
}
