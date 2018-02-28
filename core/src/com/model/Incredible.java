package com.model;
//

public class Incredible extends Character {
    private static final float AFFECTED_AREA = 50;
    private static final int BASE_PRICE = 1000;


    public Incredible(boolean users, StageOfEvolution stage) {
        super(700, 7, 2, 2, 1000, users, stage, CharacterType.FAT);
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
