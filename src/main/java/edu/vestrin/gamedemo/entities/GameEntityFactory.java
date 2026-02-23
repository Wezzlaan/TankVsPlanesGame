package edu.vestrin.gamedemo.entities;

import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import edu.vestrin.gamedemo.components.*;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.Arrays;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class GameEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        Texture t = texture("ww2_tank.png");
        t.setPreserveRatio(true);
        t.setFitWidth(150);
        t.setFitHeight(150);

        return entityBuilder(data)
            .type(EntityType.PLAYER)
            .view(t)
            .bbox(new HitBox(new Point2D(35, 0), BoundingShape.box(30, 150)))
            .with(new PlayerComponent())
            .with(new HealthIntComponent(5))
            .collidable()
            .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Texture t = texture("Light_Shell.png");
        t.setPreserveRatio(true);
        t.setFitWidth(80);
        t.setFitHeight(80);
        Point2D dir = data.get("dir");

        return entityBuilder(data)
            .type(EntityType.BULLET)
            .viewWithBBox(t)
            .with(new BulletComponent(dir, 700))
            .collidable()
            .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        EnemyType type = data.get("type");

        String textureName = "";
        HealthIntComponent enemyHealth = null;
        int points = 0;
        double size = 0;

        switch (type) {
            case NORMAL_PLANE -> {
                textureName = "plane.png";
                enemyHealth = new HealthIntComponent(3);
                points = 1;
                size = 100;
            }
            case MEDIUM_PLANE -> {
                textureName = "medium_plane.png";
                enemyHealth = new HealthIntComponent(12);
                points = 5;
                size = 130;
            }
            case LARGE_PLANE -> {
                textureName = "large_plane.png";
                enemyHealth = new HealthIntComponent(20);
                points = 20;
                size = 160;
            }
        }

        Texture t = texture(textureName);
        t.setPreserveRatio(true);
        t.setFitWidth(size);
        t.setFitHeight(size);
        t.setRotate(180);
        Point2D dir = data.get("dir");

       return entityBuilder(data)
           .type(EntityType.ENEMY)
           .viewWithBBox(t)
           .with(new EnemyComponent(dir, 80))
           .with(enemyHealth)
           .with("scoreValue", points)
           .collidable()
           .build();
    }

    @Spawns("explosion")
    public Entity newExplosion(SpawnData data) {
        AnimationChannel explosionChannel =
            new AnimationChannel(Arrays.asList(
                image("Explosion_A.png"),
                image("Explosion_B.png"),
                image("Explosion_C.png"),
                image("Explosion_D.png"),
                image("Explosion_E.png"),
                image("Explosion_F.png"),
                image("Explosion_G.png"),
                image("Explosion_H.png")
            ), Duration.seconds(0.6));
        AnimatedTexture texture = new AnimatedTexture(explosionChannel);
        texture.setScaleX(0.6);
        texture.setScaleY(0.6);

        return entityBuilder(data)
            .view(texture)
            .with(new ExpireCleanComponent(Duration.seconds(0.6)))
            .onActive(e -> texture.play())
            .build();
    }

    @Spawns("powerUp")
    public Entity newPowerUp(SpawnData data) {

        PowerUpType type = data.get("type");

        String textureName = "";

        switch (type) {
            case HEAL -> textureName = "15_burger.png";
            case NUKE -> textureName = "Heavy_Shell.png";
            case ENERGY_DRINK -> textureName = "Monster-Energy.png";
            case WALL -> textureName = "shield.png";
            case GUN -> textureName = "Assault_rifle.png";
        }

        Texture t = texture(textureName);
        t.setPreserveRatio(true);
        t.setFitWidth(100);
        t.setFitHeight(100);
        Point2D dir = data.get("dir");

        return entityBuilder(data)
            .type(EntityType.POWERUP)
            .viewWithBBox(t)
            .with(new PowerUpComponent(dir, 160))
            .with("powerUpType", type)
            .with(new ExpireCleanComponent(Duration.seconds(10)))
            .collidable()
            .build();
    }
}
