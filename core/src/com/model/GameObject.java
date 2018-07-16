package com.model;

public abstract class GameObject {
    protected float position;
    protected boolean users;
    protected StageOfEvolution stage;

    public GameObject(boolean users, float position, StageOfEvolution stage) {
        this.users = users;
        this.stage = stage;
        this.position = position;
    }

    boolean isUsers() {
        return users;
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
}
