package com.controller;

import com.exception.LimitOfEvolutionException;
import com.exception.NotEnoughMonyException;
import com.model.*;
import com.model.Character;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CharacterController {
    private static Map<Character, CharacterType> userArmy = new ConcurrentHashMap<Character, CharacterType>();
    private static Map<Character, CharacterType> gameArmy = new ConcurrentHashMap<Character, CharacterType>();
    public static StageOfEvolution userEvolveStage = StageOfEvolution.FIRST;
    public static StageOfEvolution NPCEvolveState = StageOfEvolution.FIRST;
    private static boolean userWin = false;
    private static boolean gameWin = false;
    private static boolean pause = false;
    private static boolean gameFinished = false;
    private static int totalScore = 0;
    private static int  totalMoney = 100;
    public static float clothestUserObjectPosition = UserForpost.CLOSEST_USER_OBJECT;
    public static GameObject clothestUserObject = UserForpost.getInstance();
    public static float clothestGameObjectPosition = GameForpost.CLOSEST_NPC_OBJECT;
    public static GameObject clothestGameObject = GameForpost.getInstance();
    public static Object lock = new Object();

    private static Set<Thread> allCurrentThreads = Collections.newSetFromMap(new ConcurrentHashMap<Thread, Boolean>());


    public static Character createNewCharacter(CharacterType type) throws NotEnoughMonyException {
        Character character = CharacterFactory.createCharacter(type, true, userEvolveStage);

        if (totalMoney < character.getPrice()) {
            throw new NotEnoughMonyException();
        }

        totalMoney -= character.getPrice();
        userArmy.put(character, type);
        Thread characterThread = new Thread(character);
        allCurrentThreads.add(characterThread);
        characterThread.start();

        return character;
    }

    public static void evolve() throws LimitOfEvolutionException {

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

            default: {
                throw new LimitOfEvolutionException();
            }
        }
    }

    private static Object object = new Object();

    public static boolean isPause() {
        return pause;
    }

    public static void resume() {
        pause = false;

        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public static void setPause(boolean pause) {
        CharacterController.pause = pause;
    }

    public static Map<Character, CharacterType> getUserArmy() {return userArmy;}
    public static int getUserArmyCount(){return userArmy.size();}

    public static void setUserArmy(Map<Character, CharacterType> userArmy) {CharacterController.userArmy = userArmy;}

    public static Map<Character, CharacterType> getGameArmy() {
        return gameArmy;
    }
    public static int getGameArmyCount() {return gameArmy.size();}

    public static void addCharacterToGameArmy(Character character, CharacterType type) {
        CharacterController.gameArmy.put(character, type);
    }

    public static int getTotalMoney() {
        return totalMoney;
    }

    public static void setTotalMoney(int totalMoney) {
        CharacterController.totalMoney = totalMoney;
    }

    public static int getTotalScore() {
        return totalScore;
    }

    public static void addTotalScore(int totalScore) {
        CharacterController.totalScore += totalScore;
    }

    public static void addMoney(int delta) {
        totalMoney += delta;
    }

    public static void start() {
        Thread userThread = new Thread(new UserArmyChecker());
        Thread NPCCheckerThread = new Thread(new NPCArmyChecker());
        Thread winnerThread = new Thread(new WinnerChecker());
        Thread NPCControllerThread = new Thread(new NPCController());

        userThread.setName("USER_THREAD");
        NPCCheckerThread.setName("NPC_CHECKER");
        winnerThread.setName("WINNER");
        NPCControllerThread.setName("NPC_CONTROLLER");

        allCurrentThreads.add(userThread);
        allCurrentThreads.add(NPCCheckerThread);
        allCurrentThreads.add(winnerThread);
        allCurrentThreads.add(NPCControllerThread);

        userThread.start();
        NPCCheckerThread.start();
        winnerThread.start();
        NPCControllerThread.start();
    }

    public static boolean isUserWin() {
        return userWin;
    }

    public static void setUserWin(boolean userWin) {
        CharacterController.userWin = userWin;
    }

    public static boolean isGameWin() {
        return gameWin;
    }

    public static void setGameWin(boolean gameWin) {
        CharacterController.gameWin = gameWin;
    }

    public static boolean isGameFinished() {
        return gameFinished;
    }

    public static void setGameFinished(boolean gameFinished) {
        CharacterController.gameFinished = gameFinished;
    }

    public static void addNewThread(Thread thread) {
        allCurrentThreads.add(thread);
    }

    public static void reset() {
        gameFinished = true;
        resume();

        for (Thread thread: allCurrentThreads) {
            try {
                if (thread.getName().equals("NPC_CONTROLLER")) {
                    thread.interrupt();
                }

                thread.join();
            }  catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        userArmy.clear();
        gameArmy.clear();
        allCurrentThreads.clear();
        userEvolveStage = StageOfEvolution.FIRST;
        NPCEvolveState = StageOfEvolution.FIRST;
        userWin = false;
        gameWin = false;
        pause = false;
        gameFinished = false;
        totalScore = 0;
        totalMoney = 1000;
        UserForpost.getInstance().setHealth(1000);
        GameForpost.getInstance().setHealth(1000);
    }
}
