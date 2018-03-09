package com.model;

import java.util.concurrent.TimeUnit;

//
public class Rider extends Character {
    public static final float AFFECTED_AREA = 200;
    private static final int BASE_PRICE = 150;

    public Rider(boolean users, StageOfEvolution stage) {
        super(250, 10f, 1, 8, 150, users, stage, CharacterType.RIDER);
    }

    @Override
    public void fight(GameObject gameObject) {
      /*  try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            return;
        }*/

        gameObject.setHealth(gameObject.getHealth() - getStrength());
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }

    public static int getBasePrice() {
        return BASE_PRICE;
    }

}
