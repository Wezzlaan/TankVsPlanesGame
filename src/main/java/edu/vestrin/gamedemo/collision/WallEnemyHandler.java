package edu.vestrin.gamedemo.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.vestrin.gamedemo.components.EnemyComponent;
import edu.vestrin.gamedemo.entities.EntityType;

public class WallEnemyHandler extends CollisionHandler {

    public WallEnemyHandler() {
        super(EntityType.WALL, EntityType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity wall, Entity enemy) {
        enemy.getComponent(EnemyComponent.class).die();
    }
}
