package com.controller;

import com.model.CharacterType;
import com.model.StageOfEvolution;
import com.model.UnitState;

import javax.lang.model.type.UnionType;

interface GameChangeListaner {
    void onUnitStateChange(boolean users, int unitID, UnitState newState);
    void onCoordinateChange(boolean users, int unitID, float newCoordinate);
    void onEnemyUnitCreate(CharacterType type, int id);
    void onForpostDestroy(boolean userWin);
    void onEnemyEvolveStageChange(StageOfEvolution newStage);
}
