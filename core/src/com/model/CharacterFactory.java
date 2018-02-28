package com.model;

public class CharacterFactory {
    public static Character createCharacter(CharacterType type, boolean users, StageOfEvolution stage) {

        switch (type) {
            case ARCHER: { return new Archer(users, stage); }

            case INFATRYMAN: { return new Infatryman(users, stage); }

            case FAT: { return new Fat(users, stage); }

            case RIDER: { return new Rider(users, stage); }

            case INCREDIBLE: { return new Incredible(users, stage); }

            default: {
                return null;
            }
        }

    }
}
