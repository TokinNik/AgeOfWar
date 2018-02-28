package com.model;

/**
 * Created by denis on 28.02.18.
 */

public class Fat extends Character {
    private static final float AFFECTED_AREA = 50;

    public Fat(boolean users, StageOfEvolution stage) {
        super(350, 2f, 0.8f, 0.5f, 60, users, stage, CharacterType.FAT);
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
