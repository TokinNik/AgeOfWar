package com.model;

public class Incredible extends Unit {
    private static final float AFFECTED_AREA = 50;
    private static final int BASE_PRICE = 1000;


    public Incredible(int id, boolean users, StageOfEvolution stage, Object syncObj) {
        super(id, 700, 7, 2, 2, 1000, users, stage, CharacterType.FAT, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
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
