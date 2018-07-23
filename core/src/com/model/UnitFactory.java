package com.model;

import com.controller.UnitType;

public class UnitFactory {
    public static Unit createNewUnit(UnitType type, boolean users, StageOfEvolution stage, Object syncObj) {

        switch (type) {
            case ARCHER: { return new Archer(users, stage, syncObj); }

            case INFATRYMAN: { return new Infatryman(users, stage, syncObj); }

            case FAT: { return new Fat(users, stage, syncObj); }

            case RIDER: { return new Rider(users, stage, syncObj); }

            case INCREDIBLE: { return new Incredible(users, stage, syncObj); }

            default: {
                return null;
            }
        }

    }
}
