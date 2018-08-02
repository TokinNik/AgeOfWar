package com.model;

import com.GameEvent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Unit extends VulnerableObject implements Runnable, Movable {
    private static final AtomicInteger unitId = new AtomicInteger(1);

    private int id;
    private float speed;
    private float armor;
    protected float strength;
    private int direction;
    private int price;
    private UnitState state = UnitState.WALK;
    private UnitType type;

    private VulnerableObject clothestEnemy;
    private boolean nothingChanged = true;
    private boolean unitStateChanged = true;
    private GameEvent gameState = GameEvent.PROCESSED;
    private final Object synchObj;

    public Unit(float health, float speed, float armor, float strength, int price, boolean users, StageOfEvolution stage, UnitType type, final Object synchObj) {
        super(health, users, users ? 0 : 1000, stage);
        this.id = unitId.incrementAndGet();
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

    public void changeState(UnitState newState) {
        state = newState;
        unitStateChanged = true;
        nothingChanged = false;
    }

    public void changeGameSate(GameEvent newEvent) {
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

    @Override
    public int getId() { return id; }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public UnitType getType() {
        return type;
    }
}
