package edu.vestrin.gamedemo.logic;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import edu.vestrin.gamedemo.components.EnemyType;
import edu.vestrin.gamedemo.util.ConfigUtil;
import javafx.geometry.Point2D;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnemyControl {
    /** SPAWN LOGIC **/
    public void tick() {
        int kills = geti("enemiesKilled");
        spawnEnemy();

        double rawAmount = (kills * ConfigUtil.SPAWN_SCALING_FACTOR) + ConfigUtil.BASE_ENEMY_AMOUNT;
        double finalAmount = Math.min(rawAmount, ConfigUtil.MAX_ENEMIES_PER_TICK);

        int integerPart = (int) finalAmount;
        double fractionalPart = finalAmount - integerPart;

        for (int i = 0; i < integerPart; i++) {
            spawnEnemy();
        }

        if (FXGL.random() < fractionalPart) {
            spawnEnemy();
        }
    }

    private void spawnEnemy() {

        double[] lanes = {600, 700, 800, 900, 1000, 1100, 1200};
        double randomX = lanes[FXGL.random(0, lanes.length -1)];
        double randomY = FXGL.random(-250, -100);

        EnemyType type = getRandomType();

        spawn("enemy",
            new SpawnData(randomX, randomY)
                .put("dir", new Point2D(0, 1))
                .put("type", type));
    }

    private EnemyType getRandomType() {
        double r = FXGL.random();
        int playerPoints = geti("enemiesKilled");

        if (playerPoints >= ConfigUtil.STAGE_FIVE_THRESHOLD) {
            if (r < 0.2) return EnemyType.NORMAL_PLANE;
            if (r < 0.5) return EnemyType.MEDIUM_PLANE;
            return EnemyType.LARGE_PLANE;
        }
        if (playerPoints >= ConfigUtil.STAGE_FOUR_THRESHOLD) {
            if (r < 0.3) return EnemyType.NORMAL_PLANE;
            if (r < 0.7) return EnemyType.MEDIUM_PLANE;
            return EnemyType.LARGE_PLANE;
        }
        if (playerPoints >= ConfigUtil.STAGE_THREE_THRESHOLD) {
            if (r < 0.3) return EnemyType.NORMAL_PLANE;
            if (r < 0.8) return EnemyType.MEDIUM_PLANE;
            return EnemyType.LARGE_PLANE;
        }

        if (playerPoints >= ConfigUtil.STAGE_TWO_THRESHOLD) {
            if (r < 0.4) return EnemyType.NORMAL_PLANE;
            if (r < 0.9) return EnemyType.MEDIUM_PLANE;
            return EnemyType.LARGE_PLANE;
        }

        if (playerPoints >= ConfigUtil.STAGE_ONE_THRESHOLD) {
            if (r < 0.7) return EnemyType.NORMAL_PLANE;
            return EnemyType.MEDIUM_PLANE;
        }

        return EnemyType.NORMAL_PLANE;
    }
}
