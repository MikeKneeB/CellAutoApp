package com.cellularautomata.cellautoapp;

import android.graphics.Canvas;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * Created by mike on 10/03/17.
 */

class AnimateThread extends Thread {

    private CellSurfaceView cellSurfaceView;
    private EvolveThread evolveThread;
    private boolean running = false;
    private int gameSpeed;

    private final Semaphore goFlag = new Semaphore(1, true);

    public AnimateThread(CellSurfaceView view, EvolveThread thread, int gameSpeed) {
        cellSurfaceView = view;
        evolveThread = thread;
        this.gameSpeed = gameSpeed;
    }

    public void setEvolveThread(EvolveThread evolveThread) { this.evolveThread = evolveThread; }

    public void setRunning(Boolean run) {
        running = run;
    }

    public void postTo() {
        goFlag.release();
    }

    public void drawOnce() {
        Canvas canvas = cellSurfaceView.getHolder().lockCanvas();

        if (canvas != null) {
            synchronized (cellSurfaceView.getHolder()) {
                cellSurfaceView.drawCellGrid(canvas);
            }

            cellSurfaceView.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while(running) {
            Canvas canvas = cellSurfaceView.getHolder().lockCanvas();

            if (canvas != null) {
                synchronized (cellSurfaceView.getHolder()) {
                    cellSurfaceView.drawCellGrid(canvas);
                }

                cellSurfaceView.getHolder().unlockCanvasAndPost(canvas);
            }

            evolveThread.postToo();

            try {
                sleep(gameSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
