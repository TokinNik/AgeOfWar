package com.model;
//
public class Rider extends Character {
    private static final float AFFECTED_AREA = 50;
    private static final int BASE_PRICE = 150;

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

    public static int getBasePrice() {
        return BASE_PRICE;
    }
}
