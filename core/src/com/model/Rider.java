package com.model;

/**
 * Created by denis on 28.02.18.
 */

public class Rider extends Character {
    private static final float AFFECTED_AREA = 50;

    public Rider(boolean users, StageOfEvolution stage) {
        super(250, 10f, 1, 1, 150, users, stage, CharacterType.RIDER);
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
