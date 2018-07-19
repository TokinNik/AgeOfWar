package com.controller;

import com.GameEvent;
import com.exception.LimitOfEvolutionException;
import com.exception.NotEnoughMonyException;
import com.model.*;
import com.model.Unit;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class UnitController {
    protected GameChangeListaner gameListaner;
    protected Set<Unit> userArmy = Collections.newSetFromMap(new ConcurrentHashMap<Unit, Boolean>());
    protected Set<Unit> gameArmy = Collections.newSetFromMap(new ConcurrentHashMap<Unit, Boolean>());
    private StageOfEvolution userEvolveStage = StageOfEvolution.FIRST;
    private StageOfEvolution NPCEvolveState = StageOfEvolution.FIRST;
    private boolean pause = false;
    private boolean gameFinished = false;
    private int totalScore = 0;
    private int  totalMoney = 100;
    private Gate userGate;
    private Gate gameGate;
    protected GameObject clothestUserObject = UserForpost.getInstance();
    protected GameObject clothestGameObject = GameForpost.getInstance();
    public final Object syncObj = new Object();
    private GameEvent gameState = GameEvent.PROCESSED;
    private AtomicInteger unitID = new AtomicInteger(0);
    private Executor executor = Executors.newCachedThreadPool();

    public UnitController(GameChangeListaner gameListaner) {
        this.gameListaner = gameListaner;
    }

    public int createNewUnit(CharacterType type, boolean users) throws NotEnoughMonyException {
        Unit unit = UnitFactory.createNewUnit(type, users, userEvolveStage, unitID.incrementAndGet(), syncObj);

        if (users) {
            if (totalMoney < unit.getPrice()) {
                throw new NotEnoughMonyException();
            }

            addMoney(-unit.getPrice());
            userArmy.add(unit);
        } else {
            gameListaner.onEnemyUnitCreate(type, unit.getId());
            gameArmy.add(unit);
        }

        executor.execute(unit);
        return unit.getId();
    }

    public void evolve() throws LimitOfEvolutionException {
        switch (userEvolveStage) {
            case FIRST: {
                userEvolveStage = StageOfEvolution.SECOND;
                break;
            }

            case SECOND: {
                userEvolveStage = StageOfEvolution.THIRD;
                break;
            }

            case THIRD: {
                userEvolveStage = StageOfEvolution.FOURTH;
                break;
            }
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void resume() {
        pause = false;

        synchronized (syncObj) {
            syncObj.notifyAll();
        }
    }

    public void setPause(boolean pause) {
        this.pause = pause;
        for (Unit unit: gameArmy) {
            unit.changeGameSate(GameEvent.PAUSED);
        }
        for (Unit unit: userArmy) {
            unit.changeGameSate(GameEvent.PAUSED);
        }
    }

    public StageOfEvolution getUserEvolveStage() {
        return userEvolveStage;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addTotalScore(int totalScore) {
        this.totalScore += totalScore;
    }

    public void setGameState(GameEvent gameState) {
        this.gameState = gameState;
    }

    public void setUserGate(Gate userGate) {
        this.userGate = userGate;
    }

    public Gate getUserGate() {
        return userGate;
    }

    public void setGameGate(Gate gameGate) {
        this.gameGate = gameGate;
    }

    public Gate getGameGate() {
        return gameGate;
    }

    public void addMoney(int delta) {
        totalMoney += delta;
        gameListaner.onMonyCountChange(totalMoney);
    }

    public void start() {
        Thread userThread = new Thread(new UserArmyChecker(this, syncObj));
        Thread NPCCheckerThread = new Thread(new NPCArmyChecker(this, syncObj));
        Thread winnerThread = new Thread(new WinnerChecker(this, syncObj));
        Thread NPCControllerThread = new Thread(new NPCController(this, syncObj));

        userThread.setName("USER_THREAD");
        NPCCheckerThread.setName("NPC_CHECKER");
        winnerThread.setName("WINNER");
        NPCControllerThread.setName("NPC_CONTROLLER");

        userThread.start();
        NPCCheckerThread.start();
        winnerThread.start();
        NPCControllerThread.start();
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public int getCharacterPrice(CharacterType type) {
        switch (type) {
            case ARCHER:{ return (int) (Archer.getBasePrice() * userEvolveStage.getCoefficient() ) / 10 * 10; }

            case INFATRYMAN:{ return (int) (Infatryman.getBasePrice() * userEvolveStage.getCoefficient() ) / 10 * 10; }

            case INCREDIBLE:{ return (int) (Incredible.getBasePrice() * userEvolveStage.getCoefficient() ) / 10 * 10; }

            case RIDER:{ return (int) (Rider.getBasePrice() * userEvolveStage.getCoefficient() ) / 10 * 10; }

            case FAT:{ return (int) (Fat.getBasePrice() * userEvolveStage.getCoefficient() ) / 10 * 10; }
        }

        return 0;
    }

    public void reset() {
        gameFinished = true;
        resume();
        userArmy.clear();
        gameArmy.clear();
        userEvolveStage = StageOfEvolution.FIRST;
        NPCEvolveState = StageOfEvolution.FIRST;
        gameFinished = false;
        totalScore = 0;
        totalMoney = 1000;
        UserForpost.getInstance().setHealth(1000);
        GameForpost.getInstance().setHealth(1000);
    }
}
