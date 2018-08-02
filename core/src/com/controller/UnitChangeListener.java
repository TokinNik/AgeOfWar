package com.controller;

public interface UnitChangeListener {
    void onCoordinateChange(boolean users, int unitID, float newCoordinate);
    void onHealthChange(boolean users, int unitID, float health);
}
