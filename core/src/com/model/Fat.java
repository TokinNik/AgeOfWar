package com.model;

import com.controller.UnitType;

import java.util.concurrent.TimeUnit;

public class Fat extends Unit {
    private static final float AFFECTED_AREA = 50;
    public static final int BASE_PRICE = 60;


    protected Fat(boolean users, StageOfEvolution stage, Object syncObj) {
        super(350, 2f, 0.8f, 15f, 60, users, stage, UnitType.FAT, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            return;
        }

        gameObject.setHealth(gameObject.getHealth() - getStrength());
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }
}
