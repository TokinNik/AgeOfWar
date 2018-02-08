package com.model;

public enum StageOfEvolution {
    FIRST(1),
    SECOND(1.75f),
    THIRD(3),
    FOURTH(4.5f);

    private float coefficient;

    StageOfEvolution(float coefficient) {
        this.coefficient = coefficient;
    }

    public float getCoefficient() {
        return coefficient;
    }
}
