package com.walagran.wwf

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object Utils {
    fun isWordValid(context: Context, word: String): Boolean {
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
        val words =
            ArrayList(listOf(*wordLine.split(",").toTypedArray()))
        return words.contains(word.lowercase())
    }
}