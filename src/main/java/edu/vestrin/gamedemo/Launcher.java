package edu.vestrin.gamedemo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.Texture;
import edu.vestrin.gamedemo.collision.BulletEnemyHandler;
import edu.vestrin.gamedemo.collision.PowerUpPlayerHandler;
import edu.vestrin.gamedemo.collision.WallEnemyHandler;
import edu.vestrin.gamedemo.components.PlayerComponent;
import edu.vestrin.gamedemo.components.PowerUpComponent;
import edu.vestrin.gamedemo.components.PowerUpType;
import edu.vestrin.gamedemo.entities.GameEntityFactory;
import edu.vestrin.gamedemo.logic.EnemyControl;
import edu.vestrin.gamedemo.logic.PowerUpControl;
import edu.vestrin.gamedemo.util.ConfigUtil;
import edu.vestrin.gamedemo.view.GameUI;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getip;


public class Launcher  extends GameApplication {

    private Entity player;
    private final EnemyControl enemyControl = new EnemyControl();
    private final PowerUpControl powerUpControl = new PowerUpControl();
    private final GameUI ui = new GameUI();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(ConfigUtil.SCREEN_WIDTH);
        settings.setHeight(ConfigUtil.SCREEN_HEIGHT);
        settings.setMainMenuEnabled(true);
        settings.setTitle(ConfigUtil.GAME_TITLE);
        settings.setAppIcon("hamburger.png");
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        setBackground();
        startBGM();
        getGameWorld().addEntityFactory(new GameEntityFactory());
        player = getGameWorld().spawn("player", 960, 1080);
        var hpComp = player.getComponent(HealthIntComponent.class);
        getip("playerHP").bind(hpComp.valueProperty());

        getGameTimer().runAtInterval(() -> enemyControl.tick(), Duration.seconds(1));

        getip("enemiesKilled").addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= ConfigUtil.WINNING_KILLS) {
                showWinMessage();
            }
        });

        getGameTimer().runAtInterval(() -> {
            int score = geti("enemiesKilled");
            powerUpControl.spawnBasedOnScore(score);
        }, Duration.seconds(FXGL.random(4, 10)));
    }

    @Override
    protected void initInput() {

        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).shoot();
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("MoveLeft") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("MoveRight") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }
        }, KeyCode.D);
    }
    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new PowerUpPlayerHandler());
        getPhysicsWorld().addCollisionHandler(new WallEnemyHandler());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("playerHP", ConfigUtil.PLAYER_START_HEALTH);
        vars.put("playerMaxHealth", ConfigUtil.PLAYER_START_HEALTH);
        vars.put("enemiesKilled", 0);
        vars.put("burgersEaten", 0);
        vars.put("energyDrinksConsumed", 0);
    }

    @Override
    protected void initUI() {
        getGameScene().setCursorInvisible();
        ui.setup();
    }

    private void setBackground() {
        drawBorder();
        Texture bg = texture("BackgroundImage.png").copy();
        bg.setPreserveRatio(false);
        bg.setFitHeight(getAppHeight());
        bg.setFitWidth(getAppWidth());

        entityBuilder()
            .at(0,0)
            .view(bg)
            .zIndex(-100)
            .buildAndAttach();
    }

    private void startBGM() {
        var music = getAssetLoader().loadMusic("Main_Theme.wav");
        getAudioPlayer().loopMusic(music);
        getSettings().setGlobalMusicVolume(ConfigUtil.MUSIC_VOLUME);
    }

    private void showWinMessage() {
        getAudioPlayer().stopAllMusic();
        getDialogService().showMessageBox("YOU WIN THE WAR!", () -> {
            getGameController().startNewGame();
        });
    }

    private void drawBorder() {

        entityBuilder() //LEFT BORDER
            .at(0, 0)
            .view(new Rectangle(ConfigUtil.PLAY_ZONE_MIN_X, ConfigUtil.SCREEN_HEIGHT, Color.rgb(20, 20, 20, 0.9)))
            .zIndex(10)
            .buildAndAttach();

        entityBuilder() //RIGHT BORDER
            .at(ConfigUtil.PLAY_ZONE_MAX_X, 0)
            .view(new Rectangle(ConfigUtil.PLAY_ZONE_MIN_X, ConfigUtil.SCREEN_HEIGHT, Color.rgb(20, 20, 20, 0.9)))
            .zIndex(10)
            .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
