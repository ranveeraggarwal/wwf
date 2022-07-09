package com.walagran.wwf.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.walagran.wwf.R
import com.walagran.wwf.Utils
import com.walagran.wwf.ui.common.ControlsBar
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.create_game_controls_bar,
                ControlsBar.newInstance("Main Activity",
                    showHome = false,
                    showTitle = false))
            .commit()

        setUpButtons()
    }

    private fun setUpButtons() {
        findViewById<View>(R.id.create_game).setOnClickListener {
            startActivity(Intent(applicationContext,
                CreateGameActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.play_game).setOnClickListener {
            gameCode.ifPresent { gameCode: String? ->
                val intent = Intent(applicationContext,
                    GameActivity::class.java)
                intent.putExtra("GAME_CODE", gameCode)
                startActivity(intent)
                finish()
            }
        }
    }

    private val gameCode: Optional<String>
        get() {
            val gameCodeEditText = findViewById<EditText>(R.id.game_code)
            gameCodeEditText.filters = arrayOf<InputFilter>(AllCaps())

            val gameCode = gameCodeEditText.text.toString()
            return if (gameCode.length == 5 && Utils.isWordValid(
                    applicationContext,
                    gameCode)
            ) {
                Optional.of(gameCode)
            } else Optional.empty()
        }
}