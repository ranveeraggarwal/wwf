package com.walagran.wwf.ui

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.walagran.wwf.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class GameActivityTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun keyboardKeyPress() {
        launchActivityWithGameCode("FLAGS")
        pressKeys("a")
        onView(withId(R.id.game_cell_11)).check(matches(withText("A")))
    }

    @Test
    fun winGame_inFirstTurn_fromGameCodeIntent() {
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "s", "h", "enter")

        checkWonGame()
    }

    @Test
    fun winGame_inLastTurn_fromGameCodeIntent() {
        launchActivityWithGameCode("FLAGS")

        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")

        checkWonGame()
    }

    @Test
    fun loseGame_fromGameCodeIntent() {
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")

        checkLostGame()
    }

    private fun checkWonGame() {
        onView(withId(R.id.play_game_end_game_text))
            .check(matches(withText("YOU WON!")))
    }

    private fun checkLostGame() {
        onView(withId(R.id.play_game_end_game_text))
            .check(matches(withText("BETTER LUCK NEXT TIME ...")))
    }

    private fun pressKeys(vararg keys: String) {
        keys.forEach {
            onView(withId(context.resources.getIdentifier("key_$it",
                "id",
                context.packageName)))
                .perform(click())
        }
    }

    private fun launchActivityWithGameCode(gameCode: String) {
        val intent = Intent(context, GameActivity::class.java)
            .putExtra("GAME_CODE", gameCode)
        ActivityScenario.launch<GameActivity>(intent)
    }
}