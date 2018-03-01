package com.model;

import java.util.concurrent.TimeUnit;

//
public class Infatryman extends Character{
    private static final float AFFECTED_AREA = 20f;
    private static final int BASE_PRICE = 20;

    public Infatryman(boolean users, StageOfEvolution stage) {
        super(100, 7f, 0.3f, 5f, 20, users, stage, CharacterType.INFATRYMAN);
    }

    @Override
    public void fight(GameObject gameObject) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            return;
        }

        if ((gameObject instanceof Character) && ( (Character) gameObject).getType() == CharacterType.RIDER) {
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
