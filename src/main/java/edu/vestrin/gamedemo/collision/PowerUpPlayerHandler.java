package edu.vestrin.gamedemo.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.vestrin.gamedemo.components.EnemyComponent;
import edu.vestrin.gamedemo.components.PlayerComponent;
import edu.vestrin.gamedemo.components.PowerUpType;
import edu.vestrin.gamedemo.entities.EntityType;
import edu.vestrin.gamedemo.util.ConfigUtil;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.ObjectInputFilter;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PowerUpPlayerHandler extends CollisionHandler {
    public PowerUpPlayerHandler() {
        super(EntityType.PLAYER, EntityType.POWERUP);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity powerUp) {

        PowerUpType type = powerUp.getObject("powerUpType");
        powerUp.removeFromWorld();

        switch (type) {
            case HEAL -> {
                var hp = player.getComponent(HealthIntComponent.class);
                int currentHealth = hp.getValue();

                if (hp.getValue() <= ConfigUtil.PLAYER_MAX_HEALTH) {
                    hp.setValue(currentHealth + 1);
                    inc("playerMaxHealth", 1);
                }

                inc("burgersEaten", 1);

                FXGL.play("Eating.wav");
            }
            case NUKE -> {
                var enemies = FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY);
                for (Entity enemy : enemies) {
                    enemy.getComponent(EnemyComponent.class).die();

                    FXGL.inc("enemiesKilled", 1);
                }
                FXGL.play("big_explosion.wav");
            }
            case ENERGY_DRINK -> {
                player.getComponent(PlayerComponent.class).boostSpeed();
                FXGL.play("can_open_fix.wav");
                inc("energyDrinksConsumed", 1);
            }
            case WALL -> {
                Rectangle wallRectangle = new Rectangle(200, 10, ConfigUtil.WALL_COLOR);
                wallRectangle.setOpacity(0.8);

               Entity wall = entityBuilder()
                   .type(EntityType.WALL)
                   .viewWithBBox(wallRectangle)
                   .collidable()
                   .with(new ExpireCleanComponent(Duration.seconds(ConfigUtil.WALL_DURATION)))
                   .buildAndAttach();

               FXGL.play("Shield.wav");

               wall.addComponent(new Component() {
                   @Override
                   public void onUpdate(double tpf) {
                       entity.setPosition(player.getCenter().getX() - 75, player.getY() - 40);
                   }
               });
            }
            case GUN -> {
                player.getComponent(PlayerComponent.class).upgradeWeapon();
                FXGL.play("reload.wav");
            }
        }
    }
}
