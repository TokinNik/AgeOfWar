package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.Unit;
import com.model.UnitFactory;
import com.model.CharacterType;
import com.graphics.GameScreen;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class NPCController implements Runnable {
    private static final Map<CharacterType, Map<Integer, CharacterType[]>> TAMPLATES_OF_RESPOND = new HashMap<CharacterType, Map<Integer, CharacterType[]>>();
    private static final Map<CharacterType, Float[]> chanceOfCreate = new TreeMap<CharacterType, Float[]>();
    private Date timeOfAgeStart = new Date();
    private static float friquency = 0.4f;
    private static float tamplatesFriquent = 0;
    private static final Integer first = 1;
    private static final Integer second = 2;
    private static final Integer third = 3;
    private UnitController controller;
    private final Object syncObj;

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

        chanceOfCreate.put(CharacterType.INFATRYMAN, new Float[] {0.8f, 0.6f,  0.4f, 0.35f, 0.4f, 0.35f,  0.3f, 0.25f});
        chanceOfCreate.put(CharacterType.ARCHER, new Float[]     {  1f,   1f,  0.8f, 0.75f, 0.7f, 0.65f, 0.55f, 0.45f});
        chanceOfCreate.put(CharacterType.FAT, new Float[]        {  0f,   0f,    1f,    1f, 0.9f, 0.85f,  0.7f, 0.65f});
        chanceOfCreate.put(CharacterType.RIDER, new Float[]      {  0f,   0f,    0f,    0f,   1f,    1f,  0.9f, 0.85f});
        chanceOfCreate.put(CharacterType.INCREDIBLE, new Float[] {  0f,   0f,    0f,    0f,   0f,    0f,    1f,    1f});
    }

    public NPCController(UnitController controller, Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        while (true) {

            if (controller.isPause()) {
                synchronized (syncObj) {
                    try {
                        syncObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (controller.isGameFinished()) {
                break;
            }

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                break;
            }

            if (Math.random() < tamplatesFriquent) {
                respondCharacter();
                continue;
            }

            if (Math.random() < friquency) {
                randomCharacter();
            }

        }
    }

    private void randomCharacter() {
        int num = (int)((new Date().getTime() - timeOfAgeStart.getTime()) / 36000 );
        num = (num > 7) ? 7 : num;
        float rand = (float) Math.random();

        friquency = 0.4f + (num * 0.02f);

        if (num == 3) {
            tamplatesFriquent = 0.1f;
        }

        for (Map.Entry<CharacterType, Float[] > entry : chanceOfCreate.entrySet()) {
            if (rand < entry.getValue()[num]) {
                try {
                    controller.createNewUnit(entry.getKey(), false);
                } catch (NotEnoughMonyException e) {
                }
                break;
            }
        }

    }

    private void respondCharacter() {

        if (controller.clothestUserObject.getPosition() > 200 ) {
            CharacterType type = ( (Unit) controller.clothestUserObject).getType();
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
                try {
                    controller.createNewUnit(characterType, false);
                } catch (NotEnoughMonyException e) {
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(800);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
