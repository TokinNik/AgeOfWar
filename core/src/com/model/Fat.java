package com.model;

import java.util.concurrent.TimeUnit;

public class Fat extends Unit {
    private static final float AFFECTED_AREA = 50;
    private static final int BASE_PRICE = 60;


    public Fat(int id, boolean users, StageOfEvolution stage, Object syncObj) {
        super(id, 350, 2f, 0.8f, 15f, 60, users, stage, CharacterType.FAT, syncObj);
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

    public static int getBasePrice() {
        return BASE_PRICE;
    }
}
