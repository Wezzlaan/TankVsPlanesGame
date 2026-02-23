package edu.vestrin.gamedemo.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import edu.vestrin.gamedemo.entities.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.*;
import static java.lang.Math.clamp;
import static java.lang.Math.exp;

public class EnemyComponent extends Component {

    private Point2D direction;
    private double speed;
    private boolean dead = false;

    public EnemyComponent(Point2D direction, double speed) {
        this.direction = direction.normalize();
        this.speed = speed;
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }

    public void onUpdate(double tpf) {
        if (dead) return;

        double fixedTpf = Math.min(tpf, 0.2);
        entity.translate(direction.multiply(speed * fixedTpf));

        if (entity.getY() > getAppHeight()) {
            takePlayerLife();
        }
    }

    public void die() {
        if (dead) return;
        dead = true;

        int points = entity.getInt("scoreValue");

        inc("enemiesKilled", points);

        var explosion = getAssetLoader().loadSound("explosion.wav");
        explosion.getAudio().setVolume(0.01);

        FXGL.getAudioPlayer().playSound(explosion);
        spawn("explosion", entity.getCenter().subtract(130, 110));
        entity.removeFromWorld();
    }

    public boolean isDead() {
        return dead;
    }

    private void takePlayerLife() {
        dead = true;

        var player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
        var hp = player.getComponent(HealthIntComponent.class);

        hp.damage(1);
        play("punch.wav");

        Rectangle flash = new Rectangle(getAppWidth(), getAppHeight(), Color.RED);
        flash.setOpacity(0.1);
        getGameScene().addUINode(flash);
        getGameTimer().runOnceAfter(() -> getGameScene().removeUINode(flash), Duration.seconds(0.2));

        getGameScene().getViewport().shake(5.0, 0.0);

        if (hp.isZero()) {
            gameOver();
        } else {
            entity.removeFromWorld();
        }
    }

    private void gameOver() {
        int score = getip("enemiesKilled").intValue();
        int burgers = getip("burgersEaten").intValue();

        FXGL.getAudioPlayer().pauseAllMusic();

        double oldVolume = FXGL.getSettings().getGlobalMusicVolume();
        FXGL.getSettings().setGlobalSoundVolume(0.1);
        FXGL.play("shitFix.wav");
        FXGL.getSettings().setGlobalSoundVolume(oldVolume);

        getDialogService().showMessageBox("GAME OVER! \nScore: " + score + "\nBurgers consumed: " + burgers, () -> {
            getGameController().startNewGame();
        });

        entity.removeFromWorld();
    }
}
