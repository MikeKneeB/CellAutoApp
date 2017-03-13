package com.cellularautomata.cellautoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();

        ((Spinner) findViewById(R.id.spinner2)).setSelection(intent.getIntExtra("gameId", 0));
        ((EditText) findViewById(R.id.editText3)).setText(new Integer(intent.getIntExtra("cellSize", 10)).toString());
        ((EditText) findViewById(R.id.editText4)).setText(new Integer(intent.getIntExtra("gameSpeed", 100)).toString());
    }

    public void acceptSetting(View view) {
//        settingActivityCallback.passUpSettings(
//                (int) ((Spinner) findViewById(R.id.spinner2)).getSelectedItemId(),
//                Integer.parseInt(((EditText) findViewById(R.id.editText3)).getText().toString()),
//                Integer.parseInt(((EditText) findViewById(R.id.editText4)).getText().toString())
//        );
        Intent intent = new Intent();
        intent.putExtra("gameId", (int) ((Spinner) findViewById(R.id.spinner2)).getSelectedItemId());
        intent.putExtra("cellSize", Integer.parseInt(((EditText) findViewById(R.id.editText3)).getText().toString()));
        intent.putExtra("gameSpeed", Integer.parseInt(((EditText) findViewById(R.id.editText4)).getText().toString()));
        setResult(RESULT_OK, intent);
        finish();
//        NavUtils.navigateUpFromSameTask(this);
    }
}
