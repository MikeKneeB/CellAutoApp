package com.cellularautomata.cellautoapp;

import android.graphics.Canvas;

/**
 * Created by mike on 10/03/17.
 */

class AnimateThread extends Thread {

    private CellSurfaceView drawingSurfaceView;
    private boolean running = false;

    public AnimateThread(CellSurfaceView view) {
        drawingSurfaceView = view;
    }

    public void setRunning(Boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while(running) {
            Canvas canvas = drawingSurfaceView.getHolder().lockCanvas();

            if (canvas != null) {
                synchronized (drawingSurfaceView.getHolder()) {
                    drawingSurfaceView.drawCellGrid(canvas);
                }

                drawingSurfaceView.getHolder().unlockCanvasAndPost(canvas);
            }

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
