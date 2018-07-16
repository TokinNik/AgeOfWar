package com.model;

import java.util.concurrent.TimeUnit;

//
public class Infatryman extends Unit {
    private static final float AFFECTED_AREA = 20f;
    private static final int BASE_PRICE = 20;

    public Infatryman(int id, boolean users, StageOfEvolution stage, Object syncObj) {
        super(id, 100, 7f, 0.3f, 5f, 20, users, stage, CharacterType.INFATRYMAN, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            return;
        }

        if ((gameObject instanceof Unit) && ( (Unit) gameObject).getType() == CharacterType.RIDER) {
            gameObject.setHealth(gameObject.getHealth() - ( getStrength() * 2));
        } else {
            gameObject.setHealth(gameObject.getHealth() - getStrength());
        }
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }

    public static int getBasePrice() {
        return BASE_PRICE;
    }
}
