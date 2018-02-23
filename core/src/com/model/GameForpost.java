package com.model;

public class GameForpost extends GameObject {
    private static GameForpost instance;
    public static final float CLOSEST_NPC_OBJECT = 1000;

    private GameForpost() {
        super(1000, false, 2168, StageOfEvolution.FIRST);
    }

    public static synchronized GameForpost getInstance() {
        if (instance == null) {
            instance = new GameForpost();
        }

        return instance;
    }
}
