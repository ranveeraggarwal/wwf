package com.walagran.wwf

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.random.Random

object Utils {
    fun isWordValid(context: Context, word: String): Boolean {
        return getWordList(context).contains(word.lowercase())
    }

    private fun getWordList(context: Context): ArrayList<String> {
        val assetManager = context.assets
        val wordList: InputStream
        var wordLine = ""
        try {
            wordList = assetManager.open("words.txt")
            val reader = BufferedReader(InputStreamReader(wordList))
            wordLine = reader.readLine()
            wordList.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ArrayList(listOf(*wordLine.split(",").toTypedArray()))
    }

    fun getRandomWord(context: Context): String {
        val generator = Random.Default
        val wordList = getWordList(context)
        return wordList[generator.nextInt(wordList.size)]
    }
}