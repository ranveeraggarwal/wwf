package com.walagran.wwf.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.walagran.wwf.FriendleApplication
import com.walagran.wwf.R
import com.walagran.wwf.Utils
import com.walagran.wwf.ui.common.ControlsBar
import com.walagran.wwf.ui.viewmodels.DictionaryViewModel
import com.walagran.wwf.ui.viewmodels.DictionaryViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    private val dictionaryViewModel: DictionaryViewModel by viewModels {
        DictionaryViewModelFactory((application as FriendleApplication).repository)
    }

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
        findViewById<View>(R.id.play_random_game).setOnClickListener {
            val randomWord = Utils.getRandomWord(applicationContext)
            Log.d("MAIN", "Random word is $randomWord")
            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.putExtra("GAME_CODE", randomWord)
            intent.putExtra("GAME_TYPE", "RANDOM")
            startActivity(intent)
            finish()
        }

        findViewById<View>(R.id.create_game).setOnClickListener {
            startActivity(Intent(applicationContext,
                CreateGameActivity::class.java))
            finish()
        }

        findViewById<View>(R.id.play_game).setOnClickListener {
            getGameCodeFromEditableTextField().ifPresent { gameCode: String? ->
                val intent = Intent(applicationContext,
                    GameActivity::class.java)
                intent.putExtra("GAME_CODE", gameCode)
                intent.putExtra("GAME_TYPE", "WITH_CODE")
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getGameCodeFromEditableTextField(): Optional<String> {
        val gameCodeEditText = findViewById<EditText>(R.id.game_code)
        gameCodeEditText.filters = arrayOf<InputFilter>(AllCaps())

        val gameCode = gameCodeEditText.text.toString()
        return if (gameCode.length == 5 && dictionaryViewModel.isWordInDictionary(
                gameCode)
        ) {
            Optional.of(gameCode)
        } else Optional.empty()
    }
}