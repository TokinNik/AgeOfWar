package com.model;

import com.controller.UnitType;

import java.util.concurrent.TimeUnit;

//
public class Infatryman extends Unit {
    private static final float AFFECTED_AREA = 20f;
    public static final int BASE_PRICE = 20;

    protected Infatryman(boolean users, StageOfEvolution stage, Object syncObj) {
        super(100, 7f, 0.3f, 5f, 20, users, stage, UnitType.INFATRYMAN, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            return;
        }

        if ((gameObject instanceof Unit) && ( (Unit) gameObject).getType() == UnitType.RIDER) {
            gameObject.setHealth(gameObject.getHealth() - ( getStrength() * 2));
        } else {
            gameObject.setHealth(gameObject.getHealth() - getStrength());
        }
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }
}
