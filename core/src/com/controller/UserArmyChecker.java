package com.controller;

import com.model.Character;
import com.model.CharacterType;
import com.model.GameObject;
import com.model.Rider;
import com.model.UserForpost;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UserArmyChecker implements Runnable {


    /*Check first unit of user army + remove dead unit*/
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
            float tempFirstUserPosition = UserForpost.CLOSEST_USER_OBJECT;
            GameObject tempObject = UserForpost.getInstance();

            if (CharacterController.getUserGave() != null) {
                GameObject object = CharacterController.getUserGave();
                tempFirstUserPosition = object.getPosition();
                tempObject = object;
            }

            if (tempFirstUserPosition + Rider.AFFECTED_AREA > CharacterController.clothestGameObjectPosition) {
                gameObjectsInEffectedArea.add(tempObject);
            }

            Iterator<Map.Entry<Character, CharacterType>> iterator = CharacterController.getUserArmy().entrySet().iterator();

            while (iterator.hasNext()) {
                Character temp = iterator.next().getKey();

                if (!temp.isAlive()) {
                    iterator.remove();
                } else {
                    if (temp.getPosition() > tempFirstUserPosition) {
                        tempFirstUserPosition = temp.getPosition();
                        tempObject = temp;
                    }

                    if (temp.getPosition() + Rider.AFFECTED_AREA > CharacterController.clothestGameObjectPosition) {
                        gameObjectsInEffectedArea.add(temp);
                    }
                }
            }

            CharacterController.clothestUserObject = tempObject;
            CharacterController.clothestUserObjectPosition = tempFirstUserPosition;
            CharacterController.setGroupOfClothestUserObject(gameObjectsInEffectedArea);

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}
