package edu.vestrin.gamedemo.logic;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import edu.vestrin.gamedemo.components.PowerUpType;
import edu.vestrin.gamedemo.util.ConfigUtil;
import javafx.geometry.Point2D;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class PowerUpControl {

    public void spawnMultiple(int amount) {
        double zoneWidth = ConfigUtil.PLAY_ZONE_MAX_X - ConfigUtil.PLAY_ZONE_MIN_X;
        double segmentWidth = zoneWidth / amount;
        for (int i = 0; i < amount; i++) {
            double segMin = ConfigUtil.PLAY_ZONE_MIN_X + (i * segmentWidth);
            double segMax = Math.max(segMin, segMin + segmentWidth - 100);
            double randomX = FXGL.random(segMin, segMax);

            PowerUpType type = getRandomPowerUpType();

            FXGL.spawn("powerUp",
                new SpawnData(randomX, -50)
                    .put("dir", new Point2D(0, 1))
                    .put("type", type));
        }
    }

    public void spawnBasedOnScore(int currentScore) {
        int amount = 1 + (currentScore / 1000);
        int finalAmount = Math.min(amount, 2);

        spawnMultiple(finalAmount);
    }

    private PowerUpType getRandomPowerUpType() {

        Map<PowerUpType, Integer> items = Map.of(
            PowerUpType.ENERGY_DRINK, ConfigUtil.WEIGHT_ENERGY_DRINK,
            PowerUpType.HEAL, ConfigUtil.WEIGHT_HEAL,
            PowerUpType.WALL, ConfigUtil.WEIGHT_WALL,
            PowerUpType.NUKE, ConfigUtil.WEIGHT_NUKE,
            PowerUpType.GUN, ConfigUtil.WEIGHT_GUN
        );

        int totalWeight = items.values().stream().mapToInt(Integer::intValue).sum();

        int r = FXGL.random(0, totalWeight);

        int currentSum = 0;
        for (Map.Entry<PowerUpType, Integer> entry : items.entrySet()) {
            currentSum += entry.getValue();
            if (r <= currentSum) {
                return entry.getKey();
            }
        }

        return PowerUpType.HEAL;
    }
}
