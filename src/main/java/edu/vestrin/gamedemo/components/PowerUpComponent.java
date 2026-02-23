package edu.vestrin.gamedemo.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Arrays;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static java.lang.Math.clamp;
import static java.lang.Math.exp;

public class PowerUpComponent extends Component {

    private Point2D direction;
    private double speed;

    public PowerUpComponent(Point2D direction, double speed) {
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }

    public void onUpdate(double tpf) {

        double fixedTpf = Math.min(tpf, 0.02);
        entity.translate(direction.multiply(speed * fixedTpf));
    }
}
