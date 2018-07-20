package com.model;

//
public class Rider extends Unit {
    public static final float AFFECTED_AREA = 200;
    public static final int BASE_PRICE = 150;

    protected Rider(boolean users, StageOfEvolution stage, Object syncObj) {
        super(250, 10f, 1, 8, 150, users, stage, UnitType.RIDER, syncObj);
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
}
