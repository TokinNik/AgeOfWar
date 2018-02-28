package com.model;

/**
 * Created by denis on 28.02.18.
 */

public class Incredible extends Character {
    private static final float AFFECTED_AREA = 50;

    public Incredible(boolean users, StageOfEvolution stage) {
        super(700, 7, 2, 0.5f, 1000, users, stage, CharacterType.FAT);
    }

    @Override
    public void fight(GameObject gameObject) {
        gameObject.setHealth(gameObject.getHealth() - getStrength());
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }
}
