package com.walagran.wwf.ui

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.walagran.wwf.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class GameActivityTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    // First row tests
    @Test
    fun keyboardKeyPress() {
        launchActivityWithGameCode("FLAGS")
        pressKeys("a")
        checkCellMatchesText(R.id.game_cell_11, "A")
    }

    @Test
    fun keyboardKeyPressOnce_thenEnter() {
        launchActivityWithGameCode("FLAGS")
        pressKeys("a", "enter")
        checkCellMatchesText(R.id.game_cell_11, "A")
    }

    @Test
    fun keyboardKeyPressOnce_thenBack() {
        launchActivityWithGameCode("FLAGS")
        pressKeys("a", "back")
        checkCellMatchesText(R.id.game_cell_11, "")
    }

    @Test
    fun keyboardKeyPressFiveTimes_thenBack() {
        launchActivityWithGameCode("FLAGS")
        pressKeys("a", "a", "a", "a", "a", "back")

        checkCellMatchesText(R.id.game_cell_11, "A")
        checkCellMatchesText(R.id.game_cell_12, "A")
        checkCellMatchesText(R.id.game_cell_13, "A")
        checkCellMatchesText(R.id.game_cell_14, "A")
        checkCellMatchesText(R.id.game_cell_15, "")
    }

    @Test
    fun keyboardKeyPressManyTimes_thenBack_thenEnter() {
        launchActivityWithGameCode("FLAGS")
        pressKeys("a", "a", "a", "a", "a", "a", "a", "a")
        pressKeys("back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back")
        pressKeys("enter")

        checkCellMatchesText(R.id.game_cell_11, "")
        checkCellMatchesText(R.id.game_cell_12, "")
        checkCellMatchesText(R.id.game_cell_13, "")
        checkCellMatchesText(R.id.game_cell_14, "")
        checkCellMatchesText(R.id.game_cell_15, "")
    }

    @Test
    fun winGame_inFirstTurn_fromGameCodeIntent() {
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "s", "h", "enter")

        checkWonGame()
    }

    @Test
    fun winGame_inFirstTurn_fromGameCodeIntent_checkShare() {
        Intents.init()
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "s", "h", "enter")

        onView(withId(R.id.play_game_share_buttons)).perform(click())

        intended(chooser(allOf(hasAction(Intent.ACTION_SEND),
            hasExtra(Intent.EXTRA_TEXT, "Wordle with Friends\n" +
                    "\uD83E\uDD16\uD83E\uDD16\uD83E\uDD47\uD83E\uDD47\n" +"🟩🟩🟩🟩🟩"))))

        Intents.release()
    }

    // Some row in the middle tests
    @Test
    fun getToAMiddleRow_thenPressAKey() {
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f")

        checkCellMatchesText(R.id.game_cell_21, "F")
    }

    @Test
    fun getToAMiddleRow_thenPressManyEnters() {
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("enter",
            "enter",
            "enter",
            "enter",
            "enter",
            "enter",
            "enter",
            "enter")

        checkCellMatchesText(R.id.game_cell_11, "F")
        checkCellMatchesText(R.id.game_cell_12, "L")
        checkCellMatchesText(R.id.game_cell_13, "A")
        checkCellMatchesText(R.id.game_cell_14, "G")
        checkCellMatchesText(R.id.game_cell_15, "S")
        checkCellMatchesText(R.id.game_cell_21, "")
    }

    @Test
    fun getToAMiddleRow_thenPressManyBacks() {
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "g", "s", "enter", "a")
        pressKeys("back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back",
            "back")

        checkCellMatchesText(R.id.game_cell_11, "F")
        checkCellMatchesText(R.id.game_cell_12, "L")
        checkCellMatchesText(R.id.game_cell_13, "A")
        checkCellMatchesText(R.id.game_cell_14, "G")
        checkCellMatchesText(R.id.game_cell_15, "S")
        checkCellMatchesText(R.id.game_cell_21, "")
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

    @Test
    fun loseGame_fromGameCodeIntent_checkShare() {
        Intents.init()
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")

        onView(withId(R.id.play_game_share_buttons)).perform(click())

        intended(chooser(allOf(hasAction(Intent.ACTION_SEND),
            hasExtra(Intent.EXTRA_TEXT,
                "Wordle with Friends\n" +
                        "\uD83E\uDD16\uD83E\uDD16\uD83E\uDD47\uD83E\uDD47\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8"))))

        Intents.release()
    }

    @Test
    fun winGame_fromGameCodeIntent_checkShare() {
        Intents.init()
        launchActivityWithGameCode("FLASH")

        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "g", "s", "enter")
        pressKeys("f", "l", "a", "s", "h", "enter")

        onView(withId(R.id.play_game_share_buttons)).perform(click())

        intended(chooser(allOf(hasAction(Intent.ACTION_SEND),
            hasExtra(Intent.EXTRA_TEXT,
                "Wordle with Friends\n" +
                        "\uD83E\uDD16\uD83E\uDD16\uD83E\uDD47\uD83E\uDD47\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9⬛\uD83D\uDFE8\n" +
                        "\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9\uD83D\uDFE9"))))

        Intents.release()
    }

    private fun checkWonGame() {
        onView(withId(R.id.play_game_end_game_text))
            .check(matches(withText("YOU WON!")))
    }

    private fun checkLostGame() {
        onView(withId(R.id.play_game_end_game_text))
            .check(matches(withText("BETTER LUCK NEXT TIME ...")))
    }

    private fun checkCellMatchesText(cellId: Int, text: String) {
        onView(withId(cellId)).check(matches(withText(text)))
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

    private fun chooser(matcher: Matcher<Intent?>?): Matcher<Intent?>? {
        return allOf(hasAction(Intent.ACTION_CHOOSER),
            hasExtra(`is`(Intent.EXTRA_INTENT), matcher))
    }
}