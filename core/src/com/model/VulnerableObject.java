package com.model;

public abstract class VulnerableObject extends GameObject {
    private float health;
    private float maxHealth;

    public VulnerableObject(float health, boolean users, float position, StageOfEvolution stage) {
        super(users, position, stage);
        this.health = health;
        this.maxHealth = health;
    }

    public abstract int getId();

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }
}
