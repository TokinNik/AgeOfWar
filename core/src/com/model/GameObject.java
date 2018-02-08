package com.model;

public abstract class GameObject {
    protected float health;
    protected float position;
    protected boolean alive;
    protected boolean users;
    protected StageOfEvolution stage;

    public GameObject(float health, boolean users, float position, StageOfEvolution stage) {
        this.health = health;
        this.users = users;
        this.stage = stage;
        this.position = position;
        alive = true;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isUsers() {
        return users;
    }

    public void setUsers(boolean users) {
        this.users = users;
    }

    public StageOfEvolution getStage() {
        return stage;
    }

    public void setStage(StageOfEvolution stage) {
        this.stage = stage;
    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }
}
