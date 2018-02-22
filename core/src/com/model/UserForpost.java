package com.model;

public class UserForpost extends GameObject {
    private static UserForpost instance;

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
