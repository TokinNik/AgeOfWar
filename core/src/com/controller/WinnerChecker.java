package com.controller;

import com.model.Character;
import com.model.GameForpost;
import com.model.UserForpost;

import java.util.concurrent.TimeUnit;

public class WinnerChecker implements Runnable {

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

            try {
                TimeUnit.MILLISECONDS.sleep(300);
                if (UserForpost.getInstance().getHealth() <= 0) {
                    CharacterController.setUserWin(true);
                    break;
                }

                if (GameForpost.getInstance().getHealth() <= 0) {
                    CharacterController.setGameWin(true);
                    break;
                }

            } catch (InterruptedException e) {
            }
        }
    }
}
