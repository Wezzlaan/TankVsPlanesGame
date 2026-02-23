package edu.vestrin.gamedemo.view;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.ui.Position;
import com.almasb.fxgl.ui.ProgressBar;
import edu.vestrin.gamedemo.util.ConfigUtil;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameUI {



    public void setup() {
        addHealthBar();

        addLabel("Score: %d", "enemiesKilled", 50, Color.WHITE);
        addLabel("HP: %d", "playerHP", 100, Color.RED);
        addLabel("Burgers eaten: %d", "burgersEaten", 150, Color.GREEN);
        addLabel("Energy drinks: %d", "energyDrinksConsumed", 200, Color.GREENYELLOW);

        addRightLabel("MOVEMENT: A & D", 50, Color.WHITE);
        addRightLabel("SHOOT: SPACE", 100, Color.WHITE);
    }

    private void addLabel(String format, String varName, int y, Color color) {
        Label label = new Label();
        label.setTextFill(color);
        label.setStyle("-fx-font-size: 40px;");
        label.setTranslateX(50);
        label.setTranslateY(y);
        label.textProperty().bind(FXGL.getip(varName).asString(format));
        FXGL.addUINode(label);
    }

    private void addRightLabel(String text, int y, Color color) {

        Label label = new Label(text);
        label.setTextFill(color);
        label.setStyle("-fx-font-size: 40px;");
        label.setTranslateX(1470);
        label.setTranslateY(y);
        FXGL.addUINode(label);
    }

    private void addHealthBar() {

        ProgressBar hpBar = new ProgressBar(true);
        hpBar.setMinValue(0);

        hpBar.maxValueProperty().bind(getip("playerMaxHealth"));
        hpBar.currentValueProperty().bind(getip("playerHP"));

        hpBar.setWidth(geti("playerMaxHealth") * 20);

        hpBar.setHeight(25);
        hpBar.setLabelVisible(true);
        hpBar.setLabelPosition(Position.TOP);
        hpBar.setFill(Color.GREEN);
        hpBar.setTranslateX(ConfigUtil.PLAY_ZONE_MAX_X + 20);
        hpBar.setTranslateY(1010);

        getip("playerMaxHealth").addListener((obs, oldMax, newMax) -> {
            hpBar.setWidth(newMax.intValue() * 20);

            hpBar.requestLayout();
        });

        FXGL.addUINode(hpBar);
    }
}
