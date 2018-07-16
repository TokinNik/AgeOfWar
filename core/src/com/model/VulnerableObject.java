package com.model;

public abstract class VulnerableObject extends GameObject {
    protected float health;

    public VulnerableObject(float health, boolean users, float position, StageOfEvolution stage) {
        super(users, position, stage);
        this.health = health;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
