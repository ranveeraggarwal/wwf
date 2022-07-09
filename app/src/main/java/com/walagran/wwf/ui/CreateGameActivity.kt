package com.walagran.wwf.ui

import com.walagran.wwf.ui.common.ControlsBar.Companion.newInstance
import com.walagran.wwf.ui.common.KeyboardFragment.Companion.newInstance
import androidx.appcompat.app.AppCompatActivity
import com.walagran.wwf.ui.common.KeyboardEventListener
import com.walagran.wwf.ui.CreateGameActivity.CreateGameKeyboardEventListener
import android.widget.TextView
import android.os.Bundle
import com.walagran.wwf.R
import android.content.Intent
import com.walagran.wwf.ui.MainActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import com.walagran.wwf.ui.common.ControlsBar
import com.walagran.wwf.ui.common.KeyboardFragment
import java.util.*

class CreateGameActivity : AppCompatActivity() {
    var keyboardEventListener: KeyboardEventListener =
        CreateGameKeyboardEventListener()
    var letterViews = ArrayList<TextView>()
    var createdWord = ArrayList(listOf('F', 'L', 'A', 'S', 'H'))
    var cellInFocus = 0
    var gameId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)
        setUpFragments()
        initializeLetterViews()
        initializeButtons()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initializeButtons() {
        val shareButton = findViewById<Button>(R.id.create_game_share_button)
        shareButton.setOnClickListener { view: View? ->
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            word.ifPresent { word: String? ->
                intent.putExtra(Intent.EXTRA_SUBJECT, String.format("Sharing " +
                        "Game #%s", gameId))
                intent.putExtra(Intent.EXTRA_TEXT, word)
                startActivity(Intent.createChooser(intent, "Word"))
            }
        }
    }

    private val word: Optional<String>
        private get() = if (cellInFocus == 5) Optional.of(TextUtils.join("",
            createdWord)) else Optional.empty()

    private fun initializeLetterViews() {
        for (i in 1..5) {
            letterViews
                .add(findViewById(resources
                    .getIdentifier("create_game_created_letter_$i",
                        "id",
                        packageName)))
        }
    }

    private fun setUpFragments() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.create_game_controls_bar,
                newInstance(String.format("Creating Game " +
                        "%s", gameId)))
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.create_game_keyboard,
                newInstance(keyboardEventListener))
            .commit()
    }

    internal inner class CreateGameKeyboardEventListener :
        KeyboardEventListener {
        override fun onAlphaKeyPressed(alphabet: Char) {
            if (cellInFocus == 5) {
                return
            }
            letterViews[cellInFocus].text = alphabet.toString()
            createdWord[cellInFocus] = Character.toUpperCase(alphabet)
            cellInFocus++
        }

        override fun onBackKeyPressed() {
            if (cellInFocus == 0) {
                return
            }
            cellInFocus--
            letterViews[cellInFocus].text = ""
        }

        override fun onEnterKeyPressed() {}
    }
}