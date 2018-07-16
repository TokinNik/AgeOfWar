package com.model;

public class UnitFactory {
    public static Unit createNewUnit(CharacterType type, boolean users, StageOfEvolution stage, int id, Object syncObj) {

        switch (type) {
            case ARCHER: { return new Archer(id, users, stage, syncObj); }

            case INFATRYMAN: { return new Infatryman(id, users, stage, syncObj); }

            case FAT: { return new Fat(id, users, stage, syncObj); }

            case RIDER: { return new Rider(id, users, stage, syncObj); }

            case INCREDIBLE: { return new Incredible(id, users, stage, syncObj); }

            default: {
                return null;
            }
        }

    }
}
