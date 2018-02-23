package com.controller;

import com.exception.LimitOfEvolutionException;
import com.exception.NotEnoughMonyException;
import com.model.*;
import com.model.Character;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private static int  totalMoney = 10000;
    public static float clothestUserObjectPosition = UserForpost.CLOSEST_USER_OBJECT;
    public static GameObject clothestUserObject = UserForpost.getInstance();
    public static float clothestGameObjectPosition = GameForpost.CLOSEST_NPC_OBJECT;
    public static GameObject clothestGameObject = GameForpost.getInstance();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public static Character createNewCharacter(CharacterType type) throws NotEnoughMonyException {
        Character character = CharacterFactory.createCharacter(type, true, userEvolveStage);

        if (totalMoney < character.getPrice()) {
            throw new NotEnoughMonyException();
        }

        totalMoney -= character.getPrice();
        userArmy.put(character, type);
        new Thread(character).start();

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

    public static void start() {
        new Thread(new UserArmyChecker()).start();
        new Thread(new NPCArmyChecker()).start();
        new Thread(new WinnerChecker()).start();
        new Thread(new NPCController()).start();
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
}
