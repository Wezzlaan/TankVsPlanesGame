package edu.vestrin.gamedemo.collision;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.vestrin.gamedemo.components.EnemyComponent;
import edu.vestrin.gamedemo.entities.EntityType;

public class BulletEnemyHandler extends CollisionHandler {


    public BulletEnemyHandler() {
        super(EntityType.BULLET, EntityType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity enemy) {
        bullet.removeFromWorld();

        var hp = enemy.getComponent(HealthIntComponent.class);

        hp.damage(1);

        if (hp.isZero() && !enemy.getComponent(EnemyComponent.class).isDead()) {

            enemy.getComponent(EnemyComponent.class).die();
        }
    }
}
