package com.model;

import com.controller.CharacterController;

import java.util.concurrent.TimeUnit;

public class Infatryman extends Character{

    private static final float AFFECTED_AREA = 1.5f;

    public Infatryman(boolean users, StageOfEvolution stage) {
        super(100, 0.7f, 0.3f, 1, 20, users, stage);
    }

    @Override
    public void fight(GameObject gemeObject) {
        gemeObject.setHealth(gemeObject.getHealth() - getStrength());
    }

    @Override
    public void run() {
        while (isAlive() && !CharacterController.isGameFinished()) {
            while (!CharacterController.isPause()) {
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
}
