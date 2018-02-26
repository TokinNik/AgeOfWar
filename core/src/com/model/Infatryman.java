package com.model;

import com.badlogic.gdx.Gdx;
import com.controller.CharacterController;

import java.util.concurrent.TimeUnit;

public class Infatryman extends Character{

    private static final float AFFECTED_AREA = 20f;

    public Infatryman(boolean users, StageOfEvolution stage) {
        super(100, 7f, 0.3f, 1, 20, users, stage);
    }

    @Override
    public void fight(GameObject gemeObject) {
        gemeObject.setHealth(gemeObject.getHealth() - getStrength());
    }

    @Override
    public void run() {
            while (isAlive() ) {
                if (CharacterController.isGameFinished()) {
                    break;
                }

                if (CharacterController.isPause()) {
                    synchronized (CharacterController.lock) {
                        try {
                            CharacterController.lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

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
                        move();
                    }
                } else {
                    if ((getPosition() - CharacterController.clothestUserObjectPosition) <= AFFECTED_AREA) {
                        setState(CharacterState.REDYTOFIGHT);
                        fight(CharacterController.clothestUserObject);
                    } else {
                        setState(CharacterState.REDYTOGO);
                        move();
                    }
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(1000 / (Gdx.graphics.getFramesPerSecond() + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
