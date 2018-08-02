package com.controller;

import com.GameEvent;
import com.exception.LimitOfEvolutionException;
import com.model.GameForpost;
import com.model.StageOfEvolution;
import com.model.Unit;
import com.model.UnitState;
import com.model.VulnerableObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EnemyArmyChecker implements Runnable {
    protected Set<Unit> army = Collections.newSetFromMap(new ConcurrentHashMap<Unit, Boolean>());
    private StageOfEvolution evolveState = StageOfEvolution.FIRST;
    private GameController controller;
    private final Object syncObj;
    private boolean finish;

    protected EnemyArmyChecker(GameController controller, final Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        finish = false;
        while (true) {
            if (controller.pause) {
                synchronized (syncObj) {
                    try {
                        syncObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (finish) {
                for (Unit unit : army) {
                    unit.changeGameSate(GameEvent.FINISHED);
                }

                army.clear();
                evolveState = StageOfEvolution.FIRST;
                break;
            }

            VulnerableObject furthestObject = GameForpost.getInstance();

            Iterator<Unit> iterator = army.iterator();
            while (iterator.hasNext()) {
                Unit temp = iterator.next();

                if (temp.getHealth() <= 0) {
                    temp.changeState(UnitState.DIE);
                    controller.gameListaner.onUnitStateChange(false, temp.getId(), UnitState.DIE);
                    controller.addMoney((int) Math.round(temp.getPrice() * evolveState.getCoefficient() * 1.2));
                    controller.addScore(50);
                    iterator.remove();
                } else {
                    if (temp.getPosition() < furthestObject.getPosition()) {
                        furthestObject = temp;
                    }

                    VulnerableObject vo = controller.clothestUserObject;
                    if (temp.getPosition() - temp.getAffectedArea() < vo.getPosition()) {
                        temp.setClothestEnemy(vo);
                        if (temp.getState() == UnitState.WALK) {
                            temp.changeState(UnitState.FIGHT);
                            controller.gameListaner.onUnitStateChange(false, temp.getId(), UnitState.FIGHT);
                        }
                        controller.gameListaner.onHealthChange(true, vo.getId(), vo.getHealth());
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

            controller.clothestGameObject = furthestObject;
        }
    }

    public synchronized void addWarrior(Unit unit) {
        army.add(unit);
    }

    public StageOfEvolution getEvolveState() {
        return evolveState;
    }

    public StageOfEvolution evolve() throws LimitOfEvolutionException {
        switch (evolveState) {
            case FIRST: {
                return StageOfEvolution.SECOND;
            }

            case SECOND: {
                return StageOfEvolution.THIRD;
            }

            case THIRD: {
                return StageOfEvolution.FOURTH;
            }
        }

        throw new LimitOfEvolutionException();
    }

    public void finish() {
        finish = true;
    }
}
