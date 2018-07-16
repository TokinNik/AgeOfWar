package com.model;

public class GameForpost extends VulnerableObject {
    private static GameForpost instance;
    public static final float NPC_FORPOST_COORDINATE = 1000;

    private GameForpost() {
        super(1000, false, NPC_FORPOST_COORDINATE, StageOfEvolution.FIRST);
    }

    public static synchronized GameForpost getInstance() {
        if (instance == null) {
            instance = new GameForpost();
        }

        return instance;
    }
}
