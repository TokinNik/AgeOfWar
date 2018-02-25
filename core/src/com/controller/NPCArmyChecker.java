package com.controller;

import com.model.Character;
import com.model.CharacterType;
import com.model.GameForpost;
import com.model.GameObject;

import java.util.Iterator;
import java.util.Map;

public class NPCArmyChecker implements Runnable {

    @Override
    public void run() {
        while (!CharacterController.isGameFinished()) {

            if (CharacterController.isPause()) {
                synchronized (CharacterController.lock) {
                    try {
                        CharacterController.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            float tempFirstGemeUnitPosition = GameForpost.CLOSEST_NPC_OBJECT;
            GameObject tempObject = GameForpost.getInstance();

            Iterator<Map.Entry<Character, CharacterType>> iterator = CharacterController.getGameArmy().entrySet().iterator();
            while (iterator.hasNext()) {
                Character temp = iterator.next().getKey();

                if (!temp.isAlive()) {
                    iterator.remove();
                } else {
                    if (temp.getPosition() < tempFirstGemeUnitPosition) {
                        tempFirstGemeUnitPosition = temp.getPosition();
                        tempObject = temp;
                    }
                }
            }

            CharacterController.clothestGameObject = tempObject;
            CharacterController.clothestGameObjectPosition = tempFirstGemeUnitPosition;
        }
    }
}
