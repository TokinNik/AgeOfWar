package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.Character;
import com.model.CharacterFactory;
import com.model.CharacterType;
import com.graphics.GameScreen;
import com.model.UserForpost;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NPCController implements Runnable {
    private static final Map<CharacterType, Map<Integer, CharacterType[]>> TAMPLATES_OF_RESPOND = new HashMap<CharacterType, Map<Integer, CharacterType[]>>();
    private static final Integer first = 1;
    private static final Integer second = 2;
    private static final Integer third = 3;

    static {
        Map<Integer, CharacterType[]> chance = new HashMap<Integer, CharacterType[]>();
        chance.put(first, new CharacterType[]{CharacterType.FAT, CharacterType.FAT, CharacterType.FAT, CharacterType.ARCHER, CharacterType.ARCHER});
        chance.put(second, new CharacterType[]{CharacterType.INCREDIBLE, CharacterType.ARCHER, CharacterType.ARCHER});
        TAMPLATES_OF_RESPOND.put(CharacterType.INCREDIBLE, chance);

        Map<Integer, CharacterType[]> riderChance = new HashMap<Integer, CharacterType[]>();
        riderChance.put(first, new CharacterType[] {CharacterType.INFATRYMAN, CharacterType.INFATRYMAN, CharacterType.INFATRYMAN});
        riderChance.put(second, new CharacterType[] {CharacterType.FAT, CharacterType.ARCHER});
        riderChance.put(third, new CharacterType[] {CharacterType.RIDER});
        TAMPLATES_OF_RESPOND.put(CharacterType.RIDER, riderChance);

        Map<Integer, CharacterType[]> fatChance = new HashMap<Integer, CharacterType[]>();
        fatChance.put(first, new CharacterType[] {CharacterType.INFATRYMAN, CharacterType.INFATRYMAN, CharacterType.INFATRYMAN, CharacterType.ARCHER, CharacterType.ARCHER});
        fatChance.put(second, new CharacterType[] {CharacterType.INFATRYMAN, CharacterType.ARCHER, CharacterType.ARCHER, CharacterType.ARCHER});
        fatChance.put(third, new CharacterType[] {CharacterType.FAT, CharacterType.ARCHER});
        TAMPLATES_OF_RESPOND.put(CharacterType.FAT, fatChance);

        Map<Integer, CharacterType[]> infantrymanChance = new HashMap<Integer, CharacterType[]>();
        infantrymanChance.put(first, new CharacterType[] {CharacterType.INFATRYMAN});
        infantrymanChance.put(second, new CharacterType[] {CharacterType.ARCHER});
        TAMPLATES_OF_RESPOND.put(CharacterType.INFATRYMAN, infantrymanChance);

        Map<Integer, CharacterType[]> archerChance = new HashMap<Integer, CharacterType[]>();
        archerChance.put(first, new CharacterType[] {CharacterType.ARCHER});
        archerChance.put(second, new CharacterType[] {CharacterType.INFATRYMAN});
        TAMPLATES_OF_RESPOND.put(CharacterType.ARCHER, archerChance);
    }


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

            respond();
        }
    }

    private void respond() {
        if (CharacterController.clothestUserObjectPosition > 200 ) {
            CharacterType type = ( (Character) CharacterController.clothestUserObject).getType();
            Map<Integer, CharacterType[]> tamplate = TAMPLATES_OF_RESPOND.get(type);
            CharacterType[] finalySet = null;
            float random = (float) Math.random();

            switch (type) {
                case ARCHER: {
                    if (random > 0.5f) {
                        finalySet = tamplate.get(first);
                    } else {
                        finalySet = tamplate.get(second);
                    }

                    break;
                }

                case INFATRYMAN: {
                    if (random > 0.5f) {
                        finalySet = tamplate.get(first);
                    } else {
                        finalySet = tamplate.get(second);
                    }

                    break;
                }

                case FAT: {
                    if (random > 0.6f) {
                        finalySet = tamplate.get(1);
                    } else if (random < 0.3) {
                        finalySet = tamplate.get(2);
                    } else {
                        finalySet = tamplate.get(3);
                    }

                    break;
                }

                case RIDER: {
                    if (random > 0.6f) {
                        finalySet = tamplate.get(1);
                    } else if (random < 0.3) {
                        finalySet = tamplate.get(2);
                    } else {
                        finalySet = tamplate.get(3);
                    }

                    break;
                }

                case INCREDIBLE: {
                    if (random > 0.3f) {
                        finalySet = tamplate.get(1);
                    } else {
                        finalySet = tamplate.get(2);
                    }
                }


            }

            for (CharacterType characterType : finalySet) {
                createNewCharacter(characterType);
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void createNewCharacter(CharacterType type) {
        Character character = CharacterFactory.createCharacter(type, false, CharacterController.NPCEvolveState);

        try {
            GameScreen.setCompUnit(type, character);
        } catch (NotEnoughMonyException e) {
        }

        CharacterController.addCharacterToGameArmy(character, type);

        Thread characterThread = new Thread(character);
        CharacterController.addNewThread(characterThread);
        characterThread.start();
    }
}
