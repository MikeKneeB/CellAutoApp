package com.cellularautomata.cellautoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mike on 10/03/17.
 */

public class CellSurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Paint drawPaint;
    private AnimateThread animateThread;

    public CellSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new CellSurfaceCallback());
    }

    public void drawCellGrid(Canvas canvas) {
    }

    private class CellSurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            animateThread.setRunning(true);
            animateThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;

            animateThread.setRunning(false);
            while (retry) {
                try {
                    animateThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
