package com.controller;

import com.model.Character;
import com.model.CharacterType;
import com.model.GameObject;
import com.model.UserForpost;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserArmyChecker implements Runnable {


    /*Check first unit of user army + remove dead unit*/
    @Override
    public void run() {
        while (true) {
            float tempFirstUserPosition = -21;
            GameObject tempObject = UserForpost.getInstance();

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
                }
            }

            CharacterController.clothestUserObject = tempObject;
            CharacterController.clothestUserObjectPosition = tempFirstUserPosition;

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}
