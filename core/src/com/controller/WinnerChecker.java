package com.controller;

import com.model.GameForpost;
import com.model.UserForpost;

import java.util.concurrent.TimeUnit;

public class WinnerChecker implements Runnable {
    UnitController controller;
    Object syncObj;

    public WinnerChecker(UnitController controller, Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        while (true) {

            if (controller.isPause()) {
                synchronized (controller.syncObj) {
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
                TimeUnit.MILLISECONDS.sleep(400);
                if (UserForpost.getInstance().getHealth() <= 0) {
                    controller.gameListaner.onForpostDestroy(true);
                    controller.setGameFinished(true);
                    break;
                }

                if (GameForpost.getInstance().getHealth() <= 0) {
                    controller.gameListaner.onForpostDestroy(false);
                    controller.setGameFinished(true);
                    break;
                }

            } catch (InterruptedException e) {
            }
        }
    }
}
