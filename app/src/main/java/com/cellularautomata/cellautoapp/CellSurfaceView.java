package com.cellularautomata.cellautoapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cellularautomata.cellautoapp.internal.LifeGrid;

import java.util.HashSet;

/**
 * Created by mike on 10/03/17.
 */

public class CellSurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Paint drawPaint;
    private AnimateThread animateThread;
    private EvolveThread evolveThread;
    private LifeGrid lifeGrid;

//    private int cellSize;

    private boolean running = true;

    private MenusCallback menusCallback;

    private boolean mTouchOccurred = false;
    private float mPrevX;
    private float mPrevY;

    public CellSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

//        cellSize = 10;

        init();
    }

    private void init() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        evolveThread = new EvolveThread(this);
        animateThread = new AnimateThread(this, evolveThread, CellGridActivity.gameSpeed);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new CellSurfaceCallback());
    }

    public void drawCellGrid(Canvas canvas) {
        if (this.lifeGrid == null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            HashSet<Integer> surviveList = new HashSet<Integer>();
            surviveList.add(2);
            surviveList.add(3);

            HashSet<Integer> createList = new HashSet<Integer>();
            createList.add(3);

            if (CellGridActivity.gameId == 0) {
                this.lifeGrid = new LifeGrid(width / CellGridActivity.cellSize, height / CellGridActivity.cellSize, surviveList, createList);
            }

            this.lifeGrid.randomGrid(0.3);
        }
        canvas.drawColor(Color.BLACK);
        for (int i = 0; i != this.lifeGrid.getySize(); i++) {
            for (int j = 0; j != this.lifeGrid.getxSize(); j++) {
                if (this.lifeGrid.valAt(j, i) == 1) {
                    canvas.drawRect(j*CellGridActivity.cellSize, i*CellGridActivity.cellSize, (j+1)*CellGridActivity.cellSize, (i+1)*CellGridActivity.cellSize, drawPaint);
                }
            }
        }
    }

    public void evolveGrid() {
        this.lifeGrid.evolve();
    }

    public void randomGrid() {
        lifeGrid.randomGrid(0.3);
        animateThread.drawOnce();
    }

    public void clearGrid() {
        lifeGrid.clearGrid();
        animateThread.drawOnce();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if (event.getPointerCount() == 2) {
            if (running) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        evolveThread.setRunning(false);
                        animateThread.setRunning(false);
                        boolean retry = true;
                        while (retry) {
                            try {
                                animateThread.join();
                                evolveThread.postToo();
                                evolveThread.join();
                                retry = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        animateThread.drawOnce();

                        if (menusCallback != null) {
                            menusCallback.menuState(true);
                        }

                        running = false;

                        mTouchOccurred = true;
                        return true;
                }
            } else {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_POINTER_DOWN:

                        if (menusCallback != null) {
                            menusCallback.menuState(false);
                        }

                        evolveThread = new EvolveThread(this);
                        animateThread = new AnimateThread(this, evolveThread, CellGridActivity.gameSpeed);

                        evolveThread.setRunning(true);
                        animateThread.setRunning(true);
                        animateThread.start();
                        evolveThread.start();
                        running = true;
                        mTouchOccurred = true;
                        return true;
                }
            }
        }

        if (event.getPointerCount() == 1) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                    if (mTouchOccurred) {
                        mTouchOccurred = false;
                    } else {
                        lifeGrid.placeValue((int) x / CellGridActivity.cellSize, (int) y / CellGridActivity.cellSize, -1);
                        animateThread.drawOnce();
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    int dX = ((int) x / CellGridActivity.cellSize) - ((int) mPrevX / CellGridActivity.cellSize);
                    int dY = ((int) y / CellGridActivity.cellSize) - ((int) mPrevY / CellGridActivity.cellSize);

                    if (dX != 0 || dY != 0) {
                        lifeGrid.placeValue((int) x / CellGridActivity.cellSize, (int) y / CellGridActivity.cellSize, 1);
                        animateThread.drawOnce();
                    }
            }
        }

        mPrevX = x;
        mPrevY = y;
        return true;
    }

    public void editGridValues(HashSet<Integer> surviveList, HashSet<Integer> createList) {
        lifeGrid.setCreateList(createList);
        lifeGrid.setSurviveList(surviveList);
    }

    private class CellSurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            animateThread.setRunning(true);
            evolveThread.setRunning(true);
            animateThread.start();
            evolveThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;

            animateThread.setRunning(false);
            evolveThread.setRunning(false);
            while (retry) {
                try {
                    animateThread.join();
                    evolveThread.postToo();
                    evolveThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setMenuCallback(MenusCallback menuCallback) {
        this.menusCallback = menuCallback;
    }

    public interface MenusCallback {
        void menuState(boolean state);
    }
}
