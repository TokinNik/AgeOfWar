package com.model;

import com.badlogic.gdx.Gdx;
import com.controller.CharacterController;

import java.util.concurrent.TimeUnit;

public class Infatryman extends Character{

    private static final float AFFECTED_AREA = 20f;

    public Infatryman(boolean users, StageOfEvolution stage) {
        super(100, 7f, 0.3f, 0.3f, 20, users, stage, CharacterType.INFATRYMAN);
    }

    @Override
    public void fight(GameObject gameObject) {
        if ((gameObject instanceof Character) && ( (Character) gameObject).getType() == CharacterType.RIDER) {
            gameObject.setHealth(gameObject.getHealth() - ( getStrength() * 2));
        } else {
            gameObject.setHealth(gameObject.getHealth() - getStrength());
        }
    }

    @Override
    public float getAffectedArea() {
        return AFFECTED_AREA;
    }
}
