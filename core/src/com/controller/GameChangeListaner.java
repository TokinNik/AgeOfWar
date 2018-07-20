package com.controller;

import com.model.UnitType;
import com.model.StageOfEvolution;
import com.model.UnitState;

interface GameChangeListaner {
    void onUnitStateChange(boolean users, int unitID, UnitState newState);
    void onCoordinateChange(boolean users, int unitID, float newCoordinate);
    void onHealthChange(boolean users, int unitID, float health);
    void onEnemyUnitCreate(UnitType type, int id);
    void onForpostDestroy(boolean userWin);
    void onEnemyEvolveStageChange(StageOfEvolution newStage);
    void onMonyCountChange(int value);
    void onScoreChange(int value);
}
