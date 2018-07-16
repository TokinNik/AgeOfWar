package com.model;

import com.GameEvent;

import java.util.concurrent.TimeUnit;

public abstract class Unit extends VulnerableObject implements Runnable, Movable {
    private int id;
    private float speed;
    private float armor;
    private float strength;
    private int direction;
    private int price;
    private UnitState state = UnitState.WALK;
    private CharacterType type;

    private VulnerableObject clothestEnemy;
    private boolean nothingChanged = true;
    private boolean unitStateChanged = true;
    private GameEvent gameState = GameEvent.PROCESSED;
    private final Object synchObj;

    public Unit(int id, float health, float speed, float armor, float strength, int price, boolean users, StageOfEvolution stage, CharacterType type, final Object synchObj) {
        super(health, users, users ? 0 : 1000, stage);
        this.id = id;
        this.speed = speed;
        this.armor = armor;
        this.strength = strength * stage.getCoefficient();
        this.price = (int) (price * stage.getCoefficient()) / 10 * 10;
        this.direction = users ? 1 : -1;
        this.type = type;
        this.synchObj = synchObj;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (unitStateChanged) {
                    switch (state) {
                        case WALK: {
                            nothingChanged = true;
                            while (nothingChanged) {
                                move();
                                try {
                                    TimeUnit.MILLISECONDS.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                        case FIGHT: {
                            nothingChanged = true;
                            while (nothingChanged) {
                                fight(clothestEnemy);
                                try {
                                    TimeUnit.MILLISECONDS.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                        case DIE: {
                            return;
                        }
                    }
                } else {
                    switch (gameState) {
                        case PAUSED: {
                            synchronized (synchObj) {
                                try {
                                    synchObj.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            unitStateChanged = true;
                            break;
                        }

                        default: {
                            return;
                        }
                    }
                }
            }
        }
    }

    public synchronized void changeState(UnitState newState) {
        state = newState;
        unitStateChanged = true;
        nothingChanged = false;
    }

    public synchronized void changeGameSate(GameEvent newEvent) {
        gameState = newEvent;
        unitStateChanged = false;
        nothingChanged = false;
    }

    public void move() { position += direction * speed; }

    public abstract void fight(VulnerableObject gameObject);

    public abstract float getAffectedArea();

    public void setClothestEnemy(VulnerableObject clothestEnemy) {
        this.clothestEnemy = clothestEnemy;
    }

    public UnitState getState() {
        return state;
    }

    public int getPrice() {
        return price;
    }

    public int getId() { return id; }

    public float getSpeed() {
        return speed;
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

    public CharacterType getType() {
        return type;
    }
}
