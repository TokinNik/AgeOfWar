package com.model;

/**
 * Created by denis on 08.03.18.
 */

public class Gate extends VulnerableObject {
    public Gate(boolean users, StageOfEvolution stage) {
        super(1000 * stage.getCoefficient(), users, users ? 10 : 990, stage);
    }

    @Override
    public int getId() {
        return 0;
    }
}
