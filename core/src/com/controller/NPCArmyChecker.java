package com.controller;

import com.model.GameForpost;
import com.model.GameObject;
import com.model.Rider;
import com.model.Unit;
import com.model.UnitState;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NPCArmyChecker implements Runnable {
    private UnitController controller;
    private final Object syncObj;

    public NPCArmyChecker(UnitController controller, final Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        while (true) {

            if (controller.isPause()) {
                synchronized (syncObj) {
                    try {
                        syncObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (controller.isGameFinished()) {
                break;
            }

            float tempFirstGemeUnitPosition = GameForpost.NPC_FORPOST_COORDINATE;
            GameObject tempObject = GameForpost.getInstance();


            if (controller.getGameGate() != null && controller.getGameGate().getHealth() > 0) {
                GameObject object = controller.getGameGate();
                tempFirstGemeUnitPosition = object.getPosition();
                tempObject = object;
            }

            Iterator<Unit> iterator = controller.getGameArmy().iterator();
            while (iterator.hasNext()) {
                Unit temp = iterator.next();

                if (temp.getHealth() <= 0) {
                    temp.changeState(UnitState.DIE);
                    controller.gameListaner.onUnitStateChange(false, temp.getId(), UnitState.DIE);
                    iterator.remove();
                } else {
                    if (temp.getPosition() < tempFirstGemeUnitPosition) {
                        tempFirstGemeUnitPosition = temp.getPosition();
                        tempObject = temp;
                    }

                    if (temp.getPosition() - temp.getAffectedArea() < controller.clothestUserObject.getPosition()) {
                        if (temp.getState() == UnitState.WALK) {
                            temp.changeState(UnitState.FIGHT);
                            controller.gameListaner.onUnitStateChange(false, temp.getId(), UnitState.FIGHT);
                        }
                    } else {
                        if (temp.getState() == UnitState.FIGHT) {
                            temp.changeState(UnitState.WALK);
                            controller.gameListaner.onUnitStateChange(false, temp.getId(), UnitState.WALK);
                        } else {
                            controller.gameListaner.onCoordinateChange(false, temp.getId(), temp.getPosition());
                        }
                    }
                }
            }

            controller.clothestGameObject = tempObject;
        }
    }
}
