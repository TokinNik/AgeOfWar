package com.model;

public class Incredible extends Unit {
    private static final float AFFECTED_AREA = 50;
    public static final int BASE_PRICE = 1000;


    protected Incredible(boolean users, StageOfEvolution stage, Object syncObj) {
        super(700, 7, 2, 2, 1000, users, stage, UnitType.FAT, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
        gameObject.decreaseHealth(strength);
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }
}
