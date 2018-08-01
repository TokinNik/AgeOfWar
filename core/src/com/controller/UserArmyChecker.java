package com.controller;

import com.GameEvent;
import com.exception.LimitOfEvolutionException;
import com.model.StageOfEvolution;
import com.model.Unit;
import com.model.UnitState;
import com.model.VulnerableObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class UserArmyChecker implements Runnable {
    protected Set<Unit> army = Collections.newSetFromMap(new ConcurrentHashMap<Unit, Boolean>());
    private StageOfEvolution evolveStage = StageOfEvolution.FIRST;
    private GameController controller;
    private final Object syncObj;
    private boolean finished;

    protected UserArmyChecker(GameController controller, final Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    /*Check first unit of user army + remove dead unit*/
    @Override
    public void run() {
        finished = false;
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

            if (finished) {
                for (Unit unit: army) {
                    unit.changeGameSate(GameEvent.FINISHED);
                }
                army.clear();
                evolveStage = StageOfEvolution.FIRST;
                break;
            }

            VulnerableObject furthestObject = controller.clothestUserObject;

            Iterator<Unit> iterator = army.iterator();

            while (iterator.hasNext())
            {
                Unit temp = iterator.next();

                if (temp.getHealth() <= 0)
                {
                    temp.changeState(UnitState.DIE);
                    controller.gameListaner.onUnitStateChange(true, temp.getId(), UnitState.DIE);
                    iterator.remove();
                }
                else
                    {
                    if (temp.getPosition() > furthestObject.getPosition())
                    {
                        furthestObject = temp;
                    }

                    VulnerableObject vo = controller.clothestGameObject;
                    if (temp.getPosition() + temp.getAffectedArea() > vo.getPosition())
                    {

                        temp.setClothestEnemy(vo);
                        if (temp.getState() == UnitState.WALK)
                        {
                            temp.changeState(UnitState.FIGHT);
                            controller.gameListaner.onUnitStateChange(true, temp.getId(), UnitState.FIGHT);
                            controller.gameListaner.onHealthChange(false, vo.getId(), vo.getHealth());
                        }
                    }
                    else
                        {
                        if (temp.getState() == UnitState.FIGHT)
                        {
                            temp.changeState(UnitState.WALK);
                            controller.gameListaner.onUnitStateChange(true, temp.getId(), UnitState.WALK);
                        }
                        else
                            {
                            controller.gameListaner.onCoordinateChange(true, temp.getId(), temp.getPosition());
                        }
                    }
                }
            }

            controller.clothestUserObject = furthestObject;

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void addWarrior(Unit unit) {
        army.add(unit);
    }

    public StageOfEvolution getEvolveStage() {
        return evolveStage;
    }

    public StageOfEvolution evolve() throws LimitOfEvolutionException {
        switch (evolveStage) {
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
        finished = true;
    }
}
