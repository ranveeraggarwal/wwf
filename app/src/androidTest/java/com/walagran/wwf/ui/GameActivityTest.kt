package com.walagran.wwf.ui

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.walagran.wwf.R
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class GameActivityTest () {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun keyboardKeyPress() {
        launchActivityWithGameCode()
        pressKeys("a")
        onView(withId(R.id.game_cell_11)).check(matches(withText("A")))
    }

    @Test
    fun finishGameFromGameCodeIntent() {
        launchActivityWithGameCode()
        pressKeys("f", "l", "a", "s", "h", "enter")
        onView(withId(R.id.play_game_end_game_text)).check(matches(isDisplayed()))
    }

    private fun pressKeys(vararg keys: String) {
        keys.forEach { onView(withId(context.resources.getIdentifier("key_$it", "id", context.packageName))).perform(
            click()) }
    }

    private fun launchActivityWithGameCode() {
        val intent = Intent(context, GameActivity::class.java).putExtra("GAME_CODE", "FLASH")
        ActivityScenario.launch<GameActivity>(intent)
    }
}