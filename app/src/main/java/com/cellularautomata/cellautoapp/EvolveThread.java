package com.cellularautomata.cellautoapp;

import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

/**
 * Created by mike on 10/03/17.
 */

public class EvolveThread extends Thread {

    private CellSurfaceView cellSurfaceView;
    private AnimateThread animateThread;
    private boolean running = false;
    private final Semaphore goFlag = new Semaphore(0, true);

    public EvolveThread(CellSurfaceView view, AnimateThread animateThread) {
        cellSurfaceView = view;
        this.animateThread = animateThread;
    }

    public void setAnimateThread(AnimateThread animateThread) { this.animateThread = animateThread; }

    public void setRunning(Boolean run) { running = run; }

    public void postToo() {
        goFlag.release();
    }

    public void run() {
        while (running) {
            try {
                goFlag.acquire();
                cellSurfaceView.evolveGrid();
                animateThread.postTo();
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

}
