package com.model;

public class UserForpost extends GameObject {
    private static UserForpost instance;
    public static final float CLOSEST_USER_OBJECT = 0;

    private UserForpost() {
        super(1000, true, 100, StageOfEvolution.FIRST);
    }

    public static synchronized UserForpost getInstance() {
        if (instance == null) {
            instance = new UserForpost();
        }

        return instance;
    }
}
