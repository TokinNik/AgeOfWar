package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterFactory;
import com.model.CharacterType;

import java.util.concurrent.TimeUnit;

public class NPCController implements Runnable {

    @Override
    public void run() {
        while (true) {
            createNewCharacter(CharacterType.INFATRYMAN);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            createNewCharacter(CharacterType.ARCHER);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void createNewCharacter(CharacterType type) {
        Character character = CharacterFactory.createCharacter(type, false, CharacterController.NPCEvolveState);

        CharacterController.addCharacterToGameArmy(character, type);
        new Thread(character).start();
    }
}
