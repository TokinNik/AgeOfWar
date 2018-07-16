package com.model;

public class UserForpost extends VulnerableObject {
    private static UserForpost instance;
    public static final float USER_FORPOST_COODRINATE = 0;

    private UserForpost() {
        super(1000, true, USER_FORPOST_COODRINATE, StageOfEvolution.FIRST);
    }

    public static synchronized UserForpost getInstance() {
        if (instance == null) {
            instance = new UserForpost();
        }

        return instance;
    }
}
