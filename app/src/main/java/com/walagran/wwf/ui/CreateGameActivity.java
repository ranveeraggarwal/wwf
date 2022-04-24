package com.walagran.wwf.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.walagran.wwf.R;
import com.walagran.wwf.ui.common.ControlsBar;
import com.walagran.wwf.ui.common.KeyboardEventListener;
import com.walagran.wwf.ui.common.KeyboardFragment;

import org.w3c.dom.Text;

public class CreateGameActivity extends AppCompatActivity implements KeyboardEventListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controlsBar, ControlsBar.newInstance("Creating Game 0"))
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_game_keyboard, KeyboardFragment.newInstance(this))
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onAlphaKeyPressed(char alphabet) {
        Log.e("Something", String.valueOf(alphabet));
        TextView cl = findViewById(R.id.create_letter_1);
        cl.setText(String.valueOf(alphabet));
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void onEnterKeyPressed() {

    }

}