package com.cellularautomata.cellautoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private int gameId = 0;
    private int gameSpeed = 100;
    private int cellSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToGrid(View view) {
        Intent intent = new Intent(this, CellGridActivity.class);
        intent.putExtra("gameId", gameId);
        intent.putExtra("gameSpeed", gameSpeed);
        intent.putExtra("cellSize", cellSize);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("gameId", gameId);
        intent.putExtra("gameSpeed", gameSpeed);
        intent.putExtra("cellSize", cellSize);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                gameId = intent.getIntExtra("gameId", 0);
                gameSpeed = intent.getIntExtra("gameSpeed", 100);
                cellSize = intent.getIntExtra("cellSize", 10);
            }
        }
    }
}
