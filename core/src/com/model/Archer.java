package com.model;

import java.util.concurrent.TimeUnit;

//
public class Archer extends Unit {
    private static final float AFFECTED_AREA = 200;
    public static final int BASE_PRICE = 30;

    protected Archer(boolean users, StageOfEvolution stage, Object syncObj) {
        super(50, 4f, 0.5f, 4f, BASE_PRICE, users, stage, UnitType.ARCHER, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException e) {
            return;
        }

        if ((gameObject instanceof Unit) && ( ( ((Unit) gameObject).getType() == UnitType.FAT) ||
                ((Unit) gameObject).getType() == UnitType.INCREDIBLE ) ) {
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
