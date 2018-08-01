package com.controller;

import com.model.GameForpost;
import com.model.UserForpost;

import java.util.concurrent.TimeUnit;

public class WinnerChecker implements Runnable {
    private GameController controller;
    private Object syncObj;
    private boolean finish;

    public WinnerChecker(GameController controller, Object syncObj) {
        this.controller = controller;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        finish = false;
        while (true) {
            if (controller.pause) {
                synchronized (controller.syncObj) {
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
                TimeUnit.MILLISECONDS.sleep(400);
                if (UserForpost.getInstance().getHealth() <= 0) {
                    controller.gameListaner.onForpostDestroy(false);
                    controller.finish();
                    break;
                }

                if (GameForpost.getInstance().getHealth() <= 0) {
                    controller.gameListaner.onForpostDestroy(true);
                    controller.finish();
                    break;
                }

            } catch (InterruptedException e) {
            }
        }
    }

    public void finish() {
        finish = true;
    }
}
