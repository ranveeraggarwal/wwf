package com.walagran.wwf.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.walagran.wwf.R
import com.walagran.wwf.Utils
import com.walagran.wwf.ui.common.ControlsBar
import com.walagran.wwf.ui.common.KeyboardEventListener
import com.walagran.wwf.ui.common.KeyboardFragment
import java.util.*

class GameActivity : AppCompatActivity() {
    private val keyboardEventListener: KeyboardEventListener =
        PlayGameKeyboardEventListener()

    private val textViewGridCache = ArrayList<ArrayList<TextView>>()
    private val textGrid = ArrayList<ArrayList<Char>>()

    private var correctWord: String = ""

    private var rowInFocus = 0
    private var cellInFocus = 0

    private var gameEnded = false

    // ⬛🟨🟩
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Game UI Setup.
        setUpFragments()
        createGrid()

        // Fetch the correct word from intents.
        getCorrectWord(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpFragments() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.play_game_controls_bar,
                ControlsBar.newInstance(""))
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.play_game_keyboard,
                KeyboardFragment.newInstance(keyboardEventListener))
            .commit()
    }

    /**
     * Creates a text view grid with empty boxes and legit IDs. Also populates a grid for the actual input characters.
     */
    private fun createGrid() {
        fun applyCellStyle(textView: TextView) {
            val layoutParams = TableRow.LayoutParams(
                resources!!.getDimension(R.dimen.game_cell_size).toInt(),
                resources!!.getDimension(R.dimen.game_cell_size)
                    .toInt())
            layoutParams.setMargins(4, 4, 4, 4)
            textView.layoutParams = layoutParams
            textView.setBackgroundColor(resources!!.getColor(R.color.white))
            textView.textSize =
                resources!!.getDimension(R.dimen.game_cell_text_size)
            textView.gravity = Gravity.CENTER
            textView.setTextColor(resources!!.getColor(R.color.black))
        }

        fun applyRowStyle(tableRow: TableRow) {
            val rowLayoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT)
            tableRow.layoutParams = rowLayoutParams
        }

        val layoutGrid = findViewById<TableLayout>(R.id.play_game_table)
        for (i in 1..5) {
            val textViewGridCacheRow = ArrayList<TextView>()
            val layoutRow = TableRow(this)
            applyRowStyle(layoutRow)
            layoutRow.id = resources.getIdentifier("game_row_$i", "id",
                packageName)
            for (j in 1..5) {
                val cell = TextView(this)
                cell.id = resources.getIdentifier("game_cell_$i$j",
                    "id", packageName)
                applyCellStyle(cell)
                layoutRow.addView(cell)
                textViewGridCacheRow.add(cell)
            }
            layoutGrid.addView(layoutRow)
            textViewGridCache.add(textViewGridCacheRow)
        }

        // Initialize fake values
        textGrid.add(ArrayList(listOf('A', 'B', 'C', 'R', 'E')))
        textGrid.add(ArrayList(listOf('F', 'J', 'N', 'S', 'W')))
        textGrid.add(ArrayList(listOf('G', 'K', 'O', 'T', 'X')))
        textGrid.add(ArrayList(listOf('G', 'L', 'P', 'U', 'Y')))
        textGrid.add(ArrayList(listOf('I', 'M', 'Q', 'V', 'Z')))
    }

    /**
     * Gets the game code from an intent. This could either be from a URL or from a parameter.
     */
    private fun getCorrectWord(savedInstanceState: Bundle?) {
        if (intent.action == Intent.ACTION_VIEW) {
            // We are here from a URL.
            if (intent.data != null) {
                // ToDo: Do some more validation here
                correctWord = intent.data!!.lastPathSegment!!
            }
        } else {
            if (savedInstanceState == null) {
                val extras = intent.extras
                if (extras != null) {
                    correctWord = extras.getString("GAME_CODE")!!
                }
            } else {
                correctWord =
                    savedInstanceState.getSerializable("GAME_CODE") as String
            }
        }

        correctWord = correctWord.uppercase()
    }

    internal inner class PlayGameKeyboardEventListener : KeyboardEventListener {
        override fun onAlphaKeyPressed(alphabet: Char) {
            if (gameEnded || rowInFocus == 5 || cellInFocus == 5) {
                return
            }
            textViewGridCache[rowInFocus][cellInFocus].text =
                alphabet.toString()
            textGrid[rowInFocus][cellInFocus] =
                Character.toUpperCase(alphabet)
            cellInFocus++
        }

        override fun onBackKeyPressed() {
            if (gameEnded || rowInFocus == 5 || cellInFocus == 0) {
                return
            }
            cellInFocus--
            textViewGridCache[rowInFocus][cellInFocus].text = ""
        }

        override fun onEnterKeyPressed() {
            fun highlightLetters() {
                val word: CharArray = correctWord.toCharArray()
                Log.d("Something", correctWord)
                val marked = booleanArrayOf(false, false, false, false, false)
                for (i in 0..4) {
                    val currentLetter = textGrid[rowInFocus][i]
                    if (currentLetter == word[i]) {
                        textViewGridCache[rowInFocus][i].setBackgroundColor(
                            resources!!.getColor(R.color.green))
                        word[i] = '1'
                        marked[i] = true
                    }
                }
                for (i in 0..4) {
                    if (marked[i]) continue
                    val currentLetter = textGrid[rowInFocus][i]
                    for (j in 0..4) {
                        if (currentLetter == word[j]) {
                            textViewGridCache[rowInFocus][i].setBackgroundColor(
                                resources!!.getColor(R.color.yellow))
                            word[j] = '1'
                            marked[i] = true
                            break
                        }
                    }
                }
            }

            fun endGame(win: Boolean) {
                val endGameText = findViewById<TextView>(R.id.play_game_end_game_text)
                endGameText.text = if (win) "YOU WON!" else "BETTER LUCK NEXT TIME ..."
                endGameText.visibility = View.VISIBLE
                val shareButtons =
                    findViewById<FrameLayout>(R.id.play_game_share_buttons)
                shareButtons.visibility = View.VISIBLE
                gameEnded = true
            }

            fun getWordFromRowInFocus(): Optional<String> {
                return if (cellInFocus == 5) Optional.of(TextUtils.join("",
                    textGrid[rowInFocus])) else Optional.empty()
            }

            if (gameEnded || rowInFocus == 5) {
                return
            }
            getWordFromRowInFocus().ifPresent { word: String ->
                if (word == correctWord) {
                    endGame(true)
                }
                if (Utils.isWordValid(applicationContext, word)) {
                    highlightLetters()
                    rowInFocus++
                    cellInFocus = 0
                    if (rowInFocus == 5) {
                        endGame(false)
                    }
                } else {
                    Toast.makeText(applicationContext, "Invalid Word",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}