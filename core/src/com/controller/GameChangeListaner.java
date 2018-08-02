package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.StageOfEvolution;
import com.model.UnitState;
import com.model.UnitType;

public interface GameChangeListaner extends UnitChangeListener{
    void onUnitStateChange(boolean users, int unitID, UnitState newState);
    void onEnemyUnitCreate(UnitType type, int id) throws NotEnoughMonyException;
    void onForpostDestroy(boolean userWin);
    void onEnemyEvolveStageChange(StageOfEvolution newStage);
    void onManyCountChange(int value);
    void onScoreChange(int value);
}
