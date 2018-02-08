package com.model;

public class CharacterFactory {
    public static Character createCharacter(CharacterType type, boolean users, StageOfEvolution stage) {
        if (type == CharacterType.ARCHER) {
            return new Archer(users, stage);
        }

        if (type == CharacterType.INFATRYMAN) {
            return new Infatryman(users, stage);
        }

        return null;
    }
}
