package com.model;

public class GameForpost extends GameObject {
    private static GameForpost instance;

    private GameForpost() {
        super(1000, false, 21, StageOfEvolution.FIRST);
    }

    public static synchronized GameForpost getInstance() {
        if (instance == null) {
            instance = new GameForpost();
        }

        return instance;
    }
}
