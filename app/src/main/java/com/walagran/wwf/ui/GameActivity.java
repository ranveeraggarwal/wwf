package com.walagran.wwf.ui;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.walagran.wwf.R;
import com.walagran.wwf.Utils;
import com.walagran.wwf.ui.common.ControlsBar;
import com.walagran.wwf.ui.common.KeyboardEventListener;
import com.walagran.wwf.ui.common.KeyboardFragment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class GameActivity extends AppCompatActivity {
    private Resources resources;
    private String packageName;

    private final KeyboardEventListener keyboardEventListener = new PlayGameKeyboardEventListener();
    private final ArrayList<ArrayList<TextView>> textViewGridCache = new ArrayList<>();
    private final ArrayList<ArrayList<Character>> textGrid = new ArrayList<>();
    private int rowInFocus = 0;
    private int cellInFocus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        resources =  getResources();
        packageName = getPackageName();

        setUpFragments();
        createGrid();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUpFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controlsBar, ControlsBar.newInstance("Phineas's Game 4"))
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_keyboard, KeyboardFragment.newInstance(keyboardEventListener))
                .commit();
    }

    private Optional<String> getWord() {
        return (cellInFocus == 5) ? Optional.of(TextUtils.join("", textGrid.get(rowInFocus))) : Optional.empty();
    }

    private void createGrid() {
        TableLayout layoutGrid = findViewById(R.id.gameTable);
        for (int i = 1; i < 6; i++) {
            ArrayList<TextView> textViewGridCacheRow = new ArrayList<>();
            TableRow layoutRow = new TableRow(this);

            applyRowStyle(layoutRow);
            layoutRow.setId(resources.getIdentifier("game_row_" + i, "id", packageName));

            for (int j = 1; j < 6; j++) {
                TextView cell = new TextView(this);

                cell.setId(resources.getIdentifier("game_cell_" + i + "" + j, "id", packageName));
                applyCellStyle(cell);

                layoutRow.addView(cell);
                textViewGridCacheRow.add(cell);
            }

            layoutGrid.addView(layoutRow);
            textViewGridCache.add(textViewGridCacheRow);
        }

        // Initialize fake values
        textGrid.add(new ArrayList<>(Arrays.asList('A', 'B', 'C', 'R', 'E')));
        textGrid.add(new ArrayList<>(Arrays.asList('F', 'J', 'N', 'S', 'W')));
        textGrid.add(new ArrayList<>(Arrays.asList('G', 'K', 'O', 'T', 'X')));
        textGrid.add(new ArrayList<>(Arrays.asList('G', 'L', 'P', 'U', 'Y')));
        textGrid.add(new ArrayList<>(Arrays.asList('I', 'M', 'Q', 'V', 'Z')));
    }

    private void applyCellStyle(TextView textView) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams((int) resources.getDimension(R.dimen.game_cell_size), (int) resources.getDimension(R.dimen.game_cell_size));
        layoutParams.setMargins(4, 4, 4, 4);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(getResources().getColor(R.color.teal_700));
        textView.setTextSize((int) resources.getDimension(R.dimen.game_cell_text_size));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(resources.getColor(R.color.white));
    }

    private void applyRowStyle(TableRow tableRow) {
        TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(rowLayoutParams);
    }

    class PlayGameKeyboardEventListener implements KeyboardEventListener {
        @Override
        public void onAlphaKeyPressed(char alphabet) {
            if (rowInFocus == 5) {
                return;
            }
            if (cellInFocus == 5) {
                return;
            }
            textViewGridCache.get(rowInFocus).get(cellInFocus).setText(String.valueOf(alphabet));
            textGrid.get(rowInFocus).set(cellInFocus, Character.toUpperCase(alphabet));
            cellInFocus++;
        }

        @Override
        public void onBackKeyPressed() {
            if (rowInFocus == 5) {
                return;
            }
            if (cellInFocus == 0) {
                return;
            }
            cellInFocus--;
            textViewGridCache.get(rowInFocus).get(cellInFocus).setText("");
        }

        @Override
        public void onEnterKeyPressed() {
            getWord().ifPresent(word -> {
                if (Utils.isWordValid(getApplicationContext(), word)) {
                    if (rowInFocus == 5) {
                        return;
                    }
                    rowInFocus++;
                    cellInFocus = 0;
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Word", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}