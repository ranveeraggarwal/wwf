package com.walagran.wwf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.walagran.wwf.R;
import com.walagran.wwf.ui.common.ControlsBar;
import com.walagran.wwf.ui.common.KeyboardEventListener;
import com.walagran.wwf.ui.common.KeyboardFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateGameActivity extends AppCompatActivity {
    KeyboardEventListener keyboardEventListener = new CreateGameKeyboardEventListener();
    ArrayList<TextView> letterViews = new ArrayList<>();
    ArrayList<Character> createdWord = new ArrayList<>(Arrays.asList('F', 'L', 'A', 'S', 'H'));
    int focusedLetter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        setUpFragments();
        initializeLetterViews();
    }

    public String getWord() {
        if (focusedLetter == 5) {
            return "FLASH";
        }
        return "";
    }

    private void initializeLetterViews() {
        for(int i=1; i<6; i++) {
            letterViews
                    .add((TextView) findViewById(getResources()
                            .getIdentifier("create_letter_"+i, "id",
                                    getPackageName())));
        }
    }

    private void setUpFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controlsBar, ControlsBar.newInstance("Creating Game 0"))
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_game_keyboard, KeyboardFragment.newInstance(keyboardEventListener))
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class CreateGameKeyboardEventListener implements KeyboardEventListener {
        @Override
        public void onAlphaKeyPressed(char alphabet) {
            if (focusedLetter == 5) {
                return;
            }
            letterViews.get(focusedLetter).setText(String.valueOf(alphabet));
            createdWord.set(focusedLetter, Character.toUpperCase(alphabet));
            focusedLetter++;
        }

        @Override
        public void onBackKeyPressed() {
            if (focusedLetter == 0) {
                return;
            }
            focusedLetter--;
            letterViews.get(focusedLetter).setText("");
        }

        @Override
        public void onEnterKeyPressed() {

        }
    }

}