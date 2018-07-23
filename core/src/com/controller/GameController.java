package com.controller;

import com.GameEvent;
import com.exception.LimitOfEvolutionException;
import com.exception.NotEnoughMonyException;
import com.model.*;
import com.model.Unit;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GameController {
    protected GameChangeListaner gameListaner;
    protected boolean pause = false;
    private int totalScore = 0;
    private int  totalMoney = 100;
    protected VulnerableObject clothestUserObject = UserForpost.getInstance();
    protected VulnerableObject clothestGameObject = GameForpost.getInstance();
    public final Object syncObj = new Object();
    private Executor executor = Executors.newCachedThreadPool();

    private UserArmyChecker userArmy;
    private EnemyArmyChecker enemyArmy;
    private WinnerChecker winnerChecker;
    private NPCController npcController;

    public GameController(GameChangeListaner gameListaner) {
        this.gameListaner = gameListaner;

        userArmy = new UserArmyChecker(this, syncObj);
        enemyArmy = new EnemyArmyChecker(this, syncObj);
        winnerChecker = new WinnerChecker(this, syncObj);
        npcController = new NPCController(this, syncObj);
    }

    public int createNewUnit(UnitType type, boolean users) throws NotEnoughMonyException {
        Unit unit;
        if (users) {
            if (totalMoney < getUnitPrice(type)) {
                throw new NotEnoughMonyException();
            }
            unit = UnitFactory.createNewUnit(type, users, userArmy.getEvolveStage(), syncObj);

            addMoney(-unit.getPrice());
            userArmy.addWarrior(unit);
        } else {
            unit = UnitFactory.createNewUnit(type, users, enemyArmy.getEvolveState(), syncObj);

            gameListaner.onEnemyUnitCreate(type, unit.getId());
            enemyArmy.addWarrior(unit);
        }

        executor.execute(unit);
        return unit.getId();
    }

    public void start() {
        Thread userThread = new Thread(userArmy);
        Thread NPCCheckerThread = new Thread(enemyArmy);
        Thread winnerThread = new Thread(winnerChecker);
        Thread NPCControllerThread = new Thread(npcController);

        userThread.setName("USER_THREAD");
        NPCCheckerThread.setName("NPC_CHECKER");
        winnerThread.setName("WINNER");
        NPCControllerThread.setName("NPC_CONTROLLER");

        userThread.start();
        NPCCheckerThread.start();
        winnerThread.start();
        NPCControllerThread.start();
    }

    public synchronized void pause() {
        this.pause = true;
        for (Unit unit: enemyArmy.army) {
            unit.changeGameSate(GameEvent.PAUSED);
        }
        for (Unit unit: userArmy.army) {
            unit.changeGameSate(GameEvent.PAUSED);
        }
    }

    public synchronized void resume() {
        pause = false;

        synchronized (syncObj) {
            syncObj.notifyAll();
        }
    }

    public void finish() {
        npcController.finish();
        userArmy.finish();
        enemyArmy.finish();
        winnerChecker.finish();
    }

    public void restart() {
        resume();
        finish();
        totalScore = 0;
        totalMoney = 1000;
        UserForpost.getInstance().setHealth(1000);
        GameForpost.getInstance().setHealth(1000);
        start();
    }

    public void evolve(boolean users) throws LimitOfEvolutionException {
        if (users) {
            userArmy.evolve();
        } else {
            enemyArmy.evolve();
        }
    }

    protected void addScore(int delta) {
        this.totalScore += delta;
        gameListaner.onScoreChange(totalScore);
    }

    protected void addMoney(int delta) {
        totalMoney += delta;
        gameListaner.onManyCountChange(totalMoney);
    }

    public int getUnitPrice(UnitType type) {
        float evolCoef = userArmy.getEvolveStage().getCoefficient();

        switch (type) {
            case ARCHER:{ return (int) (Archer.BASE_PRICE * evolCoef ) / 10 * 10; }

            case INFATRYMAN:{ return (int) (Infatryman.BASE_PRICE * evolCoef ) / 10 * 10; }

            case INCREDIBLE:{ return (int) (Incredible.BASE_PRICE * evolCoef ) / 10 * 10; }

            case RIDER:{ return (int) (Rider.BASE_PRICE * evolCoef ) / 10 * 10; }

            case FAT:{ return (int) (Fat.BASE_PRICE * evolCoef ) / 10 * 10; }
        }

        return 0;
    }
}
