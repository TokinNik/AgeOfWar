package com.model;

//
public class Rider extends Unit {
    public static final float AFFECTED_AREA = 200;
    private static final int BASE_PRICE = 150;

    public Rider(int id, boolean users, StageOfEvolution stage, Object syncObj) {
        super(id,250, 10f, 1, 8, 150, users, stage, CharacterType.RIDER, syncObj);
    }

    @Override
    public void fight(VulnerableObject gameObject) {
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
