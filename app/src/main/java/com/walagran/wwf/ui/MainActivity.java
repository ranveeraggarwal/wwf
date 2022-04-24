package com.walagran.wwf.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.walagran.wwf.R;
import com.walagran.wwf.ui.CreateGameActivity;
import com.walagran.wwf.ui.GameActivity;
import com.walagran.wwf.ui.common.ControlsBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controlsBar, ControlsBar.newInstance("Main Activity", false, false))
                .commit();

        Button createGameButton = findViewById(R.id.create_game);
        createGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CreateGameActivity.class);
            startActivity(intent);
            finish();
        });

        Button playGameButton = findViewById(R.id.play_game);
        playGameButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(intent);
            finish();
        });
    }
}