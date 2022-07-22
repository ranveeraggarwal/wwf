package com.walagran.wwf.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.walagran.wwf.LetterState
import com.walagran.wwf.R
import com.walagran.wwf.ui.common.ControlsBar.Companion.newInstance
import com.walagran.wwf.ui.common.KeyboardEventListener
import com.walagran.wwf.ui.common.KeyboardFragment.Companion.newInstance
import java.util.*

class CreateGameActivity : AppCompatActivity() {
    private var keyboardEventListener: KeyboardEventListener =
        CreateGameKeyboardEventListener()

    private lateinit var shareButton: Button

    var letterViews = ArrayList<TextView>()
    var createdWord = ArrayList(listOf('F', 'L', 'A', 'S', 'H'))
    var cellInFocus = 0
    private var gameId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        setUpBasicUIElements()

        initializeLetterViews()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initializeLetterViews() {
        for (i in 1..5) {
            letterViews
                .add(findViewById(resources
                    .getIdentifier("create_game_created_letter_$i",
                        "id",
                        packageName)))
        }
    }

    private fun setUpBasicUIElements() {
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

        shareButton = findViewById(R.id.create_game_share_button)
        shareButton.setOnClickListener {
            shareCreatedGame()
        }
    }

    private fun shareCreatedGame() {
        fun maybeGetInputWord(): Optional<String> {
            return if (cellInFocus == 5)
                Optional.of(TextUtils.join("", createdWord))
            else Optional.empty()
        }

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        maybeGetInputWord().ifPresent {
            intent.putExtra(Intent.EXTRA_SUBJECT,
                String.format("Sharing Game #%s", gameId))
            intent.putExtra(Intent.EXTRA_TEXT, it)

            startActivity(Intent.createChooser(intent, "Word"))
        }
    }

    internal inner class CreateGameKeyboardEventListener :
        KeyboardEventListener {
        override fun onAlphaKeyPressed(alphabet: Char) {
            if (cellInFocus == 5) { return }

            letterViews[cellInFocus].text = alphabet.toString()
            createdWord[cellInFocus] = alphabet

            cellInFocus++
        }

        override fun onBackKeyPressed() {
            if (cellInFocus == 0) { return }

            cellInFocus--

            letterViews[cellInFocus].text = ""
        }

        override fun onEnterKeyPressed(): Map<Char, LetterState> {
            shareCreatedGame()
            return emptyMap()
        }
    }
}