package com.controller;

import com.exception.NotEnoughMonyException;
import com.model.Unit;
import com.model.UnitType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class NPCController implements Runnable {
    private static final Map<UnitType, Map<Integer, UnitType[]>> TAMPLATES_OF_RESPOND = new HashMap<UnitType, Map<Integer, UnitType[]>>();
    private static final Map<UnitType, Float[]> chanceOfCreate = new TreeMap<UnitType, Float[]>();
    private Date timeOfAgeStart = new Date();
    private static float friquency = 0.4f;
    private static float tamplatesFriquent = 0;
    private static final Integer first = 1;
    private static final Integer second = 2;
    private static final Integer third = 3;
    private GameController controller;
    private final Object syncObj;
    private boolean finish;

    static {
        Map<Integer, UnitType[]> chance = new HashMap<Integer, UnitType[]>();
        chance.put(first, new UnitType[]{UnitType.FAT, UnitType.FAT, UnitType.FAT, UnitType.ARCHER, UnitType.ARCHER});
        chance.put(second, new UnitType[]{UnitType.INCREDIBLE, UnitType.ARCHER, UnitType.ARCHER});
        TAMPLATES_OF_RESPOND.put(UnitType.INCREDIBLE, chance);

        Map<Integer, UnitType[]> riderChance = new HashMap<Integer, UnitType[]>();
        riderChance.put(first, new UnitType[] {UnitType.INFATRYMAN, UnitType.INFATRYMAN, UnitType.INFATRYMAN});
        riderChance.put(second, new UnitType[] {UnitType.FAT, UnitType.ARCHER});
        riderChance.put(third, new UnitType[] {UnitType.RIDER});
        TAMPLATES_OF_RESPOND.put(UnitType.RIDER, riderChance);

        Map<Integer, UnitType[]> fatChance = new HashMap<Integer, UnitType[]>();
        fatChance.put(first, new UnitType[] {UnitType.INFATRYMAN, UnitType.INFATRYMAN, UnitType.INFATRYMAN, UnitType.ARCHER, UnitType.ARCHER});
        fatChance.put(second, new UnitType[] {UnitType.INFATRYMAN, UnitType.ARCHER, UnitType.ARCHER, UnitType.ARCHER});
        fatChance.put(third, new UnitType[] {UnitType.FAT, UnitType.ARCHER});
        TAMPLATES_OF_RESPOND.put(UnitType.FAT, fatChance);

        Map<Integer, UnitType[]> infantrymanChance = new HashMap<Integer, UnitType[]>();
        infantrymanChance.put(first, new UnitType[] {UnitType.INFATRYMAN});
        infantrymanChance.put(second, new UnitType[] {UnitType.ARCHER});
        TAMPLATES_OF_RESPOND.put(UnitType.INFATRYMAN, infantrymanChance);

        Map<Integer, UnitType[]> archerChance = new HashMap<Integer, UnitType[]>();
        archerChance.put(first, new UnitType[] {UnitType.ARCHER});
        archerChance.put(second, new UnitType[] {UnitType.INFATRYMAN});
        TAMPLATES_OF_RESPOND.put(UnitType.ARCHER, archerChance);

        chanceOfCreate.put(UnitType.INFATRYMAN, new Float[] {0.8f, 0.6f,  0.4f, 0.35f, 0.4f, 0.35f,  0.3f, 0.25f});
        chanceOfCreate.put(UnitType.ARCHER, new Float[]     {  1f,   1f,  0.8f, 0.75f, 0.7f, 0.65f, 0.55f, 0.45f});
        chanceOfCreate.put(UnitType.FAT, new Float[]        {  0f,   0f,    1f,    1f, 0.9f, 0.85f,  0.7f, 0.65f});
        chanceOfCreate.put(UnitType.RIDER, new Float[]      {  0f,   0f,    0f,    0f,   1f,    1f,  0.9f, 0.85f});
        chanceOfCreate.put(UnitType.INCREDIBLE, new Float[] {  0f,   0f,    0f,    0f,   0f,    0f,    1f,    1f});
    }

    public NPCController(GameController controller, Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        while (true) {
            if (controller.pause) {
                synchronized (syncObj) {
                    try {
                        syncObj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (finish) {
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

        for (Map.Entry<UnitType, Float[] > entry : chanceOfCreate.entrySet()) {
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
            UnitType type = ( (Unit) controller.clothestUserObject).getType();
            Map<Integer, UnitType[]> tamplate = TAMPLATES_OF_RESPOND.get(type);
            UnitType[] finalySet = null;
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

            for (UnitType characterType : finalySet) {
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

    public void finish() {
        finish = true;
    }
}
