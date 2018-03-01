package com.model;

import java.util.concurrent.TimeUnit;

//
public class Fat extends Character {
    private static final float AFFECTED_AREA = 50;
    private static final int BASE_PRICE = 60;


    public Fat(boolean users, StageOfEvolution stage) {
        super(350, 2f, 0.8f, 15f, 60, users, stage, CharacterType.FAT);
    }

    @Override
    public void fight(GameObject gameObject) {
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

    public static int getBasePrice() {
        return BASE_PRICE;
    }
}
