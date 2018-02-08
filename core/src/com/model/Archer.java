package com.model;

import com.controller.CharacterController;

import java.util.concurrent.TimeUnit;

public class Archer extends Character {
    private static final float AFFECTED_AREA = 5;

    public Archer(boolean users, StageOfEvolution stage) {
        super(50, 0.2f, 0.5f, 0.5f, 30, users, stage);
    }

    @Override
    public void fight(GameObject gameObject) {
        gameObject.setHealth(gameObject.getHealth() - getStrength());
    }

    @Override
    public void run() {
        while (isAlive()) {
            if (health <= 0) {
                setAlive(false);
                if (!isUsers()) {
                    CharacterController.setTotalMoney(CharacterController.getTotalMoney() + (int)(getPrice() * 1.5f));
                }
                continue;
            }

            if (isUsers()) {
                if ((CharacterController.clothestGameObjectPosition - getPosition()) <= AFFECTED_AREA) {
                    setState(CharacterState.REDYTOFIGHT);
                    fight(CharacterController.clothestGameObject);
                } else {
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
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
