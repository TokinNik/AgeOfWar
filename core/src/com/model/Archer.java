package com.model;

import com.badlogic.gdx.Gdx;
import com.controller.CharacterController;

import java.util.concurrent.TimeUnit;

public class Archer extends Character {
    private static final float AFFECTED_AREA = 200;

    public Archer(boolean users, StageOfEvolution stage) {
        super(50, 4f, 0.5f, 0.5f, 30, users, stage);
    }

    @Override
    public void fight(GameObject gameObject) {
        gameObject.setHealth(gameObject.getHealth() - getStrength());
    }

    @Override
    public void run() {
        while (!CharacterController.isGameFinished()) {
            while (isAlive() && !CharacterController.isPause()) {
                if (super.getHealth() <= 0) {
                    setAlive(false);
                    if (!isUsers()) {
                        CharacterController.addMoney(Math.round(getPrice() * 1.5f));
                    }
                    continue;
                }

                if (isUsers()) {
                    if ((CharacterController.clothestGameObjectPosition - getPosition()) <= AFFECTED_AREA) {
                        setState(CharacterState.REDYTOFIGHT);
                        fight(CharacterController.clothestGameObject);
                    } else {
                        setState(CharacterState.REDYTOGO);
                        move();
                    }
                } else {
                    if ((getPosition() - CharacterController.clothestUserObjectPosition) <= AFFECTED_AREA) {
                        setState(CharacterState.REDYTOFIGHT);
                        fight(CharacterController.clothestUserObject);
                    } else {
                        move();
                    }
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(1000/Gdx.graphics.getFramesPerSecond());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
