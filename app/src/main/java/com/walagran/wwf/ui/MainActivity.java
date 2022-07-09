package com.walagran.wwf.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.walagran.wwf.R;
import com.walagran.wwf.Utils;
import com.walagran.wwf.ui.common.ControlsBar;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        setUpFragments();
        setUpButtons();
    }

    private void setUpButtons() {
        findViewById(R.id.create_game).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),
                    CreateGameActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.play_game).setOnClickListener(view -> {
            getGameCode().ifPresent(gameCode -> {
                Intent intent = new Intent(getApplicationContext(),
                        GameActivity.class);
                intent.putExtra("GAME_CODE", gameCode);
                startActivity(intent);
                finish();
            });
        });
    }

    private Optional<String> getGameCode() {
        EditText gameCodeEditText = findViewById(R.id.game_code);
        gameCodeEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        String gameCode =
                gameCodeEditText.getText().toString();
        if (gameCode.length() == 5 && Utils.isWordValid(context, gameCode)) {
            return Optional.of(gameCode);
        }
        return Optional.empty();
    }

    private void setUpFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_game_controls_bar,
                        ControlsBar.newInstance("Main Activity", false, false))
                .commit();
    }
}