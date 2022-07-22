package com.walagran.wwf.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.walagran.wwf.R

/**
 * A dumb QWERTY keyboard with enter and back key.
 */
class KeyboardFragment : Fragment() {
    private lateinit var keyboardEventListener: KeyboardEventListener

    fun setKeyBoardEventListener(keyboardEventListener: KeyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(
            R.layout.fragment_keyboard,
            container,
            false)
        var alpha = 'a'
        while (alpha <= 'z') {
            val cachedAlphabet = alpha
            rootView
                .findViewById<Button>(rootView
                    .resources
                    .getIdentifier("key_$cachedAlphabet", "id", rootView
                        .context
                        .packageName))
                .setOnClickListener {
                    keyboardEventListener.onAlphaKeyPressed(cachedAlphabet.uppercaseChar())
                }
            alpha++
        }
        rootView
            .findViewById<Button>(R.id.key_back)
            .setOnClickListener { keyboardEventListener.onBackKeyPressed() }
        rootView
            .findViewById<Button>(R.id.key_enter)
            .setOnClickListener {
                val characterStateMap =
                    keyboardEventListener.onEnterKeyPressed()
                characterStateMap.forEach { (character, letterState) ->
                    rootView.findViewById<Button>(rootView.resources.getIdentifier(
                        "key_$character",
                        "id",
                        rootView.context.packageName))
                        .setBackgroundColor(rootView.resources!!.getColor(
                            letterState.color(),
                            rootView.context.theme))
                }
            }
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param keyboardEventListener Listens to the keyboard events and acts
         * accordingly.
         * @return A new instance of fragment KeyboardFragment.
         */
        @JvmStatic
        fun newInstance(keyboardEventListener: KeyboardEventListener): KeyboardFragment {
            val fragment = KeyboardFragment()
            fragment.setKeyBoardEventListener(keyboardEventListener)
            return fragment
        }
    }
}