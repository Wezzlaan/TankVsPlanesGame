package edu.vestrin.gamedemo.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BulletComponent extends Component {

    private Point2D direction;
    private double speed;

    public BulletComponent(Point2D direction, double speed) {
        this.direction = direction.normalize();
        this.speed = speed;
    }

    public void onUpdate(double tpf) {
        double fixedTpf = Math.min(tpf, 0.02);

        entity.translate(direction.multiply(speed * fixedTpf));

        if (isOutsideScreen()) {
            entity.removeFromWorld();
        }
    }

    private boolean isOutsideScreen() {
        return entity.getX() < 0 ||
            entity.getY() < 0 ||
            entity.getX() > getAppWidth() ||
            entity.getY() > getAppHeight();
    }
}
