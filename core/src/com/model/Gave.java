package com.model;

/**
 * Created by denis on 08.03.18.
 */

public class Gave extends GameObject {
    public Gave(boolean users, StageOfEvolution stage) {
        super(1000 * stage.getCoefficient(), users, users ? 10 : 990, stage);
    }
}
