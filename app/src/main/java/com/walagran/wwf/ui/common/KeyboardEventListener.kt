package com.walagran.wwf.ui.common

interface KeyboardEventListener {
    fun onAlphaKeyPressed(alphabet: Char)
    fun onBackKeyPressed()
    fun onEnterKeyPressed()
}