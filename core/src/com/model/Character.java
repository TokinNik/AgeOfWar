package com.model;

import com.badlogic.gdx.Gdx;
import com.controller.CharacterController;

import java.util.concurrent.TimeUnit;

public abstract class Character extends GameObject implements Runnable {

    private float speed;
    private float armor;
    private float strength;
    private int direction;
    private int price;
    private int expirienceForKilling;
    private int expirienceForCreating;
    private CharacterState state;
    private CharacterType type;

    public Character(float health, float speed, float armor, float strength, int price, boolean users, StageOfEvolution stage, CharacterType type) {
        super(health * stage.getCoefficient(), users, users ? 5 : 995, stage);
        this.speed = speed / 6;
        this.armor = armor;
        this.strength = strength * stage.getCoefficient();
        this.price = (int) (price * stage.getCoefficient()) / 10 * 10;
        this.direction = users ? 1 : -1;
        this.type = type;
        state = CharacterState.REDYTOGO;
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

            if (getHealth() <= 0) {
                setAlive(false);
                if (!isUsers()) {
                    CharacterController.addMoney(Math.round(getPrice() * 1.5f));
                    CharacterController.addTotalScore(100);
                }
                continue;
            }

            if (isUsers()) {
                if ((CharacterController.clothestGameObjectPosition - getPosition()) <= getAffectedArea()) {
                    setState(CharacterState.REDYTOFIGHT);
                    if (type == CharacterType.RIDER) {

                        for (GameObject object: CharacterController.getGroupOfClothestGameObject()) {
                            fight(object);
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(800);
                        } catch (InterruptedException e) {
                            return;
                        }
                    } else {
                        fight(CharacterController.clothestGameObject);
                    }
                } else {
                    setState(CharacterState.REDYTOGO);
                    move();
                }
            } else {
                if ((getPosition() - CharacterController.clothestUserObjectPosition) <= getAffectedArea()) {
                    setState(CharacterState.REDYTOFIGHT);
                    if (type == CharacterType.RIDER) {
                        for (GameObject object: CharacterController.getGroupOfClothestUserObject()) {
                            fight(object);
                        }

                        try {
                            TimeUnit.MILLISECONDS.sleep(800);
                        } catch (InterruptedException e) {
                            return;
                        }
                    } else {
                        fight(CharacterController.clothestUserObject);
                    }
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

    public void move() { position += direction * speed; }

    public abstract void fight(GameObject gameObject);

    public abstract float getAffectedArea();

    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public CharacterType getType() {
        return type;
    }
}
