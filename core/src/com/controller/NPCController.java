package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterFactory;
import com.model.CharacterType;
import com.mygdx.graphics.GameScreen;

import java.util.concurrent.TimeUnit;

public class NPCController implements Runnable {

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
                createNewCharacter(CharacterType.INFATRYMAN);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                }
                createNewCharacter(CharacterType.ARCHER);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public void createNewCharacter(CharacterType type) {
        Character character = CharacterFactory.createCharacter(type, false, CharacterController.NPCEvolveState);

        try {
            GameScreen.setCompUnit(type, character);
        } catch (NotEnoughMonyException e) {
            System.out.println("");
        }

        CharacterController.addCharacterToGameArmy(character, type);
        new Thread(character).start();
    }
}
