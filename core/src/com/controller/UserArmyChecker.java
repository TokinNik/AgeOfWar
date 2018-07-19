package com.controller;

import com.model.Unit;
import com.model.CharacterType;
import com.model.GameObject;
import com.model.Rider;
import com.model.UnitState;
import com.model.UserForpost;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UserArmyChecker implements Runnable {
    private UnitController controller;
    private final Object syncObj;

    public UserArmyChecker(UnitController controller, final Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    /*Check first unit of user army + remove dead unit*/
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

            float tempFirstUserPosition = UserForpost.USER_FORPOST_COODRINATE;
            GameObject tempObject = UserForpost.getInstance();

            if (controller.getUserGate() != null) {
                GameObject object = controller.getUserGate();
                tempFirstUserPosition = object.getPosition();
                tempObject = object;
            }

            Iterator<Unit> iterator = controller.userArmy.iterator();

            while (iterator.hasNext()) {
                Unit temp = iterator.next();

                if (temp.getHealth() < 0) {
                    iterator.remove();
                    temp.changeState(UnitState.DIE);
                    controller.gameListaner.onUnitStateChange(true, temp.getId(), UnitState.DIE);
                } else {
                    if (temp.getPosition() > tempFirstUserPosition) {
                        tempFirstUserPosition = temp.getPosition();
                        tempObject = temp;
                    }

                    if (temp.getPosition() + temp.getAffectedArea() > controller.clothestGameObject.getPosition()) {
                        if (temp.getState() == UnitState.WALK) {
                            temp.changeState(UnitState.FIGHT);
                            controller.gameListaner.onUnitStateChange(true, temp.getId(), UnitState.FIGHT);
                        }
                    } else {
                        if (temp.getState() == UnitState.FIGHT) {
                            temp.changeState(UnitState.WALK);
                            controller.gameListaner.onUnitStateChange(true, temp.getId(), UnitState.WALK);
                        } else {
                            controller.gameListaner.onCoordinateChange(true, temp.getId(), temp.getPosition());
                        }
                    }
                }
            }

            controller.clothestUserObject = tempObject;

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
