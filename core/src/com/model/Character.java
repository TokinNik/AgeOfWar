package com.model;

public abstract class Character extends GameObject implements Runnable {

    private float speed;
    private float armor;
    private float strength;
    private int direction;
    private int price;
    private int expirienceForKilling;
    private int expirienceForCreating;
    private CharacterState state;

    public Character(float health, float speed, float armor, float strength, int price, boolean users, StageOfEvolution stage) {
        super(health * stage.getCoefficient(), users, users ? 5 : 995, stage);
        this.speed = speed;
        this.armor = armor;
        this.strength = strength * stage.getCoefficient();
        this.price = (int) (price * stage.getCoefficient()) / 10 * 10;
        this.direction = users ? 1 : -1;
        state = CharacterState.REDYTOGO;
    }

    public void move() { position += direction * speed; }

    public abstract void fight(GameObject gameObject);

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

}
