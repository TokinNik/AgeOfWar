package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.StageOfEvolution;
import com.model.UnitState;

 public interface GameChangeListaner {
    void onUnitStateChange(boolean users, int unitID, UnitState newState);
    void onCoordinateChange(boolean users, int unitID, float newCoordinate);
    void onHealthChange(boolean users, int unitID, float health);
    void onEnemyUnitCreate(UnitType type, int id) throws NotEnoughMonyException;
    void onForpostDestroy(boolean userWin);
    void onEnemyEvolveStageChange(StageOfEvolution newStage);
    void onManyCountChange(int value);
    void onScoreChange(int value);
}
