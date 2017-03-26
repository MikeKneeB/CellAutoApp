package com.cellularautomata.cellautoapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.HashSet;

public class CellGridActivity extends Activity implements CellSurfaceView.MenusCallback, GridOptionDialogFragment.GridOptionCallback {

    private CellSurfaceView cellSurfaceView;

    static int cellSize;
    static int gameId;
    static int gameSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        cellSize = intent.getIntExtra("cellSize", 10);
        gameId = intent.getIntExtra("gameId", 0);
        gameSpeed = intent.getIntExtra("gameSpeed", 100);

        setContentView(R.layout.activity_cell_grid);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        this.cellSurfaceView = ((CellSurfaceView) findViewById(R.id.cellSurfaceView));

        this.cellSurfaceView.setMenuCallback(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void randomGrid(View view) {
        cellSurfaceView.randomGrid();
    }

    public void clearGrid(View view) {
        cellSurfaceView.clearGrid();
    }

    @Override
    public void menuState(boolean state) {
        if (state) {
            findViewById(R.id.controls_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.controls_layout).setVisibility(View.GONE);
        }
    }

    public void showRuleDialog(View view) {
        DialogFragment newFragment = GridOptionDialogFragment.newInstance(gameId);
        newFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void passUpOptions(HashSet<Integer> surviveList, HashSet<Integer> createList) {
        cellSurfaceView.editGridValues(surviveList, createList);
    }

    @Override
    public void passUpOptions(HashSet<Integer> surviveList, HashSet<Integer> createList, int generations) {
        cellSurfaceView.editGridValues(surviveList, createList, generations);
    }
}
