package com.walagran.wwf.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.walagran.wwf.R;
import com.walagran.wwf.Utils;
import com.walagran.wwf.ui.common.ControlsBar;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controlsBar, ControlsBar.newInstance("Phineas's Game 4"))
                .commit();

        Resources r = getResources();
        String name = getPackageName();

        TableLayout levelsTable = findViewById(R.id.gameTable);
        for (int i = 1; i < 6; i++) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(rowLayoutParams);
            row.setId(r.getIdentifier("game_row_" + i, "id", name));
            for (int j = 1; j < 6; j++) {
                TextView textView = new TextView(this);
                textView.setId(r.getIdentifier("game_cell_" + i + "" + j, "id", name));
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams((int) r.getDimension(R.dimen.game_cell_size), (int) r.getDimension(R.dimen.game_cell_size));
                layoutParams.setMargins(4, 4, 4, 4);
                textView.setLayoutParams(layoutParams);
                textView.setBackgroundColor(getResources().getColor(R.color.teal_700));
                textView.setTextSize((int) r.getDimension(R.dimen.game_cell_text_size));
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Utils.getColorFromAttribute(getApplicationContext(), com.google.android.material.R.attr.colorOnPrimary));

                row.addView(textView);
            }
            levelsTable.addView(row);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}