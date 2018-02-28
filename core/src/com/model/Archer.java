package com.model;
//
public class Archer extends Character {
    private static final float AFFECTED_AREA = 200;
    private static final int BASE_PRICE = 30;

    public Archer(boolean users, StageOfEvolution stage) {
        super(50, 4f, 0.5f, 0.5f, BASE_PRICE, users, stage, CharacterType.ARCHER);
    }

    @Override
    public void fight(GameObject gameObject) {
        if ((gameObject instanceof Character) && ( ( ((Character) gameObject).getType() == CharacterType.FAT) ||
                ((Character) gameObject).getType() == CharacterType.INCREDIBLE ) ) {
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
