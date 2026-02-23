module edu.vestrin.gamedemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires annotations;
    requires com.almasb.fxgl.core;
    requires javafx.base;
    requires com.almasb.fxgl.entity;
    requires javafx.graphics;
    requires java.desktop;

    opens edu.vestrin.gamedemo to javafx.fxml;
    exports edu.vestrin.gamedemo;
    opens assets.textures to com.almasb.fxgl.all;
    opens assets.sounds to com.almasb.fxgl.all;
    opens assets.music to com.almasb.fxgl.all;
    exports edu.vestrin.gamedemo.components;
    opens edu.vestrin.gamedemo.components to javafx.fxml;
    exports edu.vestrin.gamedemo.entities;
    opens edu.vestrin.gamedemo.entities to javafx.fxml;
    exports edu.vestrin.gamedemo.util;
    opens edu.vestrin.gamedemo.util to javafx.fxml;
    exports edu.vestrin.gamedemo.logic;
    opens edu.vestrin.gamedemo.logic to javafx.fxml;
    opens edu.vestrin.gamedemo.view to javafx.fxml;
    exports edu.vestrin.gamedemo.view;
    exports edu.vestrin.gamedemo.collision;
    opens edu.vestrin.gamedemo.collision to javafx.fxml;
}
