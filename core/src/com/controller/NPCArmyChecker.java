package com.controller;

import com.model.Character;
import com.model.CharacterType;
import com.model.GameForpost;
import com.model.GameObject;
import com.model.Rider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NPCArmyChecker implements Runnable {

    @Override
    public void run() {
        while (true) {

            if (CharacterController.isPause()) {
                synchronized (CharacterController.lock) {
                    try {
                        CharacterController.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (CharacterController.isGameFinished()) {
                break;
            }

            Set<GameObject> gameObjectsInEffectedArea = new HashSet<GameObject>();
            float tempFirstGemeUnitPosition = GameForpost.CLOSEST_NPC_OBJECT;
            GameObject tempObject = GameForpost.getInstance();

            if (CharacterController.getGameGave() != null) {
                GameObject object = CharacterController.getGameGave();
                tempFirstGemeUnitPosition = object.getPosition();
                tempObject = object;
            }

            if (CharacterController.clothestUserObjectPosition + Rider.AFFECTED_AREA > tempFirstGemeUnitPosition) {
                gameObjectsInEffectedArea.add(tempObject);
            }


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

                    if (CharacterController.clothestUserObjectPosition + Rider.AFFECTED_AREA > temp.getPosition()) {
                        gameObjectsInEffectedArea.add(temp);
                    }
                }
            }

            CharacterController.clothestGameObject = tempObject;
            CharacterController.clothestGameObjectPosition = tempFirstGemeUnitPosition;
            CharacterController.setGroupOfClothestGameObject(gameObjectsInEffectedArea);
        }
    }
}
