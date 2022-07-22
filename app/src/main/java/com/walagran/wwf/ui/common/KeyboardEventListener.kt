package com.walagran.wwf.ui.common

import com.walagran.wwf.LetterState

interface KeyboardEventListener {
    fun onAlphaKeyPressed(alphabet: Char)
    fun onBackKeyPressed()
    fun onEnterKeyPressed() : Map<Char, LetterState>
}