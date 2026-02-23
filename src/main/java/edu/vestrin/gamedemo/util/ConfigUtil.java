package edu.vestrin.gamedemo.util;

import javafx.scene.paint.Color;

public final class ConfigUtil {

    private ConfigUtil() {}

    //================== GENERAL GAME CONFIG ==================
    public static final String GAME_TITLE = "Tank vs Planes";
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;
    public static final double PLAY_ZONE_MIN_X = 560;
    public static final double PLAY_ZONE_MAX_X = 1360;
    public static final int WINNING_KILLS = 20000;
    public static final double MUSIC_VOLUME = 0.5;

    //================== GAME STAGES THRESHOLD CONFIG ==================
    public static final int STAGE_ONE_THRESHOLD = 50;
    public static final int STAGE_TWO_THRESHOLD = 150;
    public static final int STAGE_THREE_THRESHOLD = 350;
    public static final int STAGE_FOUR_THRESHOLD = 600;
    public static final int STAGE_FIVE_THRESHOLD = 1200;

    //================== PLAYER CONFIG ==================
    public static final int PLAYER_START_HEALTH = 5;
    public static final int PLAYER_MAX_HEALTH = 15;
    public static final double PLAYER_START_MOVESPEED = 300.0;
    public static final double PLAYER_BOOSTED_MOVESPEED = 300.0;
    public static final int PLAYER_MAX_WEAPONS = 9;

    //================== ENEMY CONFIG ==================
    public static final double BASE_ENEMY_AMOUNT = 1.0;
    public static final double SPAWN_SCALING_FACTOR = 0.32 / 100.0;
    public static final int MAX_ENEMIES_PER_TICK = 7;

    //================== POWER-UP DROP WEIGHTS ==================
    public static final int WEIGHT_ENERGY_DRINK = 30;
    public static final int WEIGHT_HEAL = 25;
    public static final int WEIGHT_WALL = 17;
    public static final int WEIGHT_NUKE = 5;
    public static final int WEIGHT_GUN = 40;

    //================== POWER-UP CONFIG ==================
    public static final double WALL_DURATION = 10.0;
    public static final double ENERGY_DURATION = 8.0;

    //================== UI CONFIG ==================
    public static final Color WALL_COLOR = Color.LIGHTBLUE;
}
