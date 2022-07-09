package com.walagran.wwf.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
    private var correctWord: String? = "FLASH"
    private var context: Context? = null
    private var rowInFocus = 0
    private var cellInFocus = 0
    private var gameEnded = false

    // ⬛🟨🟩
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        context = applicationContext
        setUpFragments()
        createGrid()
        getGameCode(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent): Boolean {
        val appLinkAction = intent.action
        val appLinkData = intent.data
        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
            correctWord = appLinkData.lastPathSegment
            return true
        }
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getGameCode(savedInstanceState: Bundle?) {
        if (handleIntent(intent)) {
            return
        }
        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras != null) {
                correctWord = extras.getString("GAME_CODE")
            }
        } else {
            correctWord = savedInstanceState.getSerializable(
                "GAME_CODE") as String?
        }
        correctWord = correctWord!!.uppercase()
    }

    private fun setUpFragments() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.play_game_controls_bar,
                ControlsBar.newInstance("Phineas's" +
                        " Game 4"))
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.play_game_keyboard,
                KeyboardFragment.newInstance(keyboardEventListener))
            .commit()
    }

    private val word: Optional<String>
        get() = if (cellInFocus == 5) Optional.of(TextUtils.join("",
            textGrid[rowInFocus])) else Optional.empty()

    private fun createGrid() {
        val layoutGrid = findViewById<TableLayout>(R.id.play_game_table)
        for (i in 1..5) {
            val textViewGridCacheRow = ArrayList<TextView>()
            val layoutRow = TableRow(this)
            applyRowStyle(layoutRow)
            layoutRow.id = resources!!.getIdentifier("game_row_$i", "id",
                packageName)
            for (j in 1..5) {
                val cell = TextView(this)
                cell.id = resources!!.getIdentifier("game_cell_$i$j",
                    "id", packageName)
                applyCellStyle(cell)
                layoutRow.addView(cell)
                textViewGridCacheRow.add(cell)
            }
            layoutGrid.addView(layoutRow)
            textViewGridCache.add(textViewGridCacheRow)
        }

        // Initialize fake values
        textGrid.add(ArrayList(Arrays.asList('A', 'B', 'C', 'R', 'E')))
        textGrid.add(ArrayList(Arrays.asList('F', 'J', 'N', 'S', 'W')))
        textGrid.add(ArrayList(Arrays.asList('G', 'K', 'O', 'T', 'X')))
        textGrid.add(ArrayList(Arrays.asList('G', 'L', 'P', 'U', 'Y')))
        textGrid.add(ArrayList(Arrays.asList('I', 'M', 'Q', 'V', 'Z')))
    }

    private fun applyCellStyle(textView: TextView) {
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

    private fun applyRowStyle(tableRow: TableRow) {
        val rowLayoutParams =
            TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT)
        tableRow.layoutParams = rowLayoutParams
    }

    private fun highlightLetters() {
        val word: CharArray = correctWord!!.toCharArray()
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

    private fun endGame(win: Boolean) {
        val endGameText = findViewById<TextView>(R.id.play_game_end_game_text)
        endGameText.text = if (win) "YOU WON!" else "BETTER LUCK NEXT TIME ..."
        endGameText.visibility = View.VISIBLE
        val shareButtons =
            findViewById<FrameLayout>(R.id.play_game_share_buttons)
        shareButtons.visibility = View.VISIBLE
        gameEnded = true
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
            if (gameEnded || rowInFocus == 5) {
                return
            }
            word.ifPresent { word: String ->
                if (word == correctWord) {
                    endGame(true)
                }
                if (Utils.isWordValid(context, word)) {
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