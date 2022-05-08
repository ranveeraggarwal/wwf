package com.walagran.wwf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.walagran.wwf.R;
import com.walagran.wwf.ui.common.ControlsBar;
import com.walagran.wwf.ui.common.KeyboardEventListener;
import com.walagran.wwf.ui.common.KeyboardFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class CreateGameActivity extends AppCompatActivity {
    KeyboardEventListener keyboardEventListener =
            new CreateGameKeyboardEventListener();
    ArrayList<TextView> letterViews = new ArrayList<>();
    ArrayList<Character> createdWord = new ArrayList<>(Arrays.asList('F', 'L'
            , 'A', 'S', 'H'));
    int cellInFocus = 0;
    int gameId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        setUpFragments();
        initializeLetterViews();
        initializeButtons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initializeButtons() {
        Button shareButton = findViewById(R.id.create_game_share_button);
        shareButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            getWord().ifPresent(word -> {
                intent.putExtra(Intent.EXTRA_SUBJECT, String.format("Sharing " +
                        "Game #%s", gameId));
                intent.putExtra(Intent.EXTRA_TEXT, word);
                startActivity(Intent.createChooser(intent, "Word"));
            });
        });
    }

    private Optional<String> getWord() {
        return (cellInFocus == 5) ? Optional.of(TextUtils.join("",
                createdWord)) : Optional.empty();
    }

    private void initializeLetterViews() {
        for (int i = 1; i < 6; i++) {
            letterViews
                    .add(findViewById(getResources()
                            .getIdentifier("create_game_created_letter_" + i,
                                    "id",
                                    getPackageName())));
        }
    }

    private void setUpFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_game_controls_bar,
                        ControlsBar.newInstance(String.format("Creating Game " +
                                "%s", gameId)))
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_game_keyboard,
                        KeyboardFragment.newInstance(keyboardEventListener))
                .commit();
    }

    class CreateGameKeyboardEventListener implements KeyboardEventListener {
        @Override
        public void onAlphaKeyPressed(char alphabet) {
            if (cellInFocus == 5) {
                return;
            }
            letterViews.get(cellInFocus).setText(String.valueOf(alphabet));
            createdWord.set(cellInFocus, Character.toUpperCase(alphabet));
            cellInFocus++;
        }

        @Override
        public void onBackKeyPressed() {
            if (cellInFocus == 0) {
                return;
            }
            cellInFocus--;
            letterViews.get(cellInFocus).setText("");
        }

        @Override
        public void onEnterKeyPressed() {

        }
    }

}