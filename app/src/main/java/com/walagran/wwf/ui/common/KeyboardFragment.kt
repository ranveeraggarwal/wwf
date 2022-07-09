package com.walagran.wwf.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.walagran.wwf.R

/**
 * A dumb QWERTY keyboard with enter and back key.
 */
class KeyboardFragment : Fragment() {
    private var keyboardEventListener: KeyboardEventListener? = null
    fun setKeyBoardEventListener(
        keyboardEventListener: KeyboardEventListener?,
    ) {
        this.keyboardEventListener = keyboardEventListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_keyboard,
            container, false)
        var alpha = 'a'
        while (alpha <= 'z') {
            val passedAlphabet = alpha
            rootView
                .findViewById<View>(rootView
                    .resources
                    .getIdentifier("key_$alpha", "id", rootView
                        .context
                        .packageName))
                .setOnClickListener {
                    keyboardEventListener!!.onAlphaKeyPressed(Character.toUpperCase(
                        passedAlphabet))
                }
            alpha++
        }
        rootView
            .findViewById<View>(R.id.key_back)
            .setOnClickListener { keyboardEventListener!!.onBackKeyPressed() }
        rootView
            .findViewById<View>(R.id.key_enter)
            .setOnClickListener { keyboardEventListener!!.onEnterKeyPressed() }
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
        fun newInstance(
            keyboardEventListener: KeyboardEventListener,
        ): KeyboardFragment {
            val fragment = KeyboardFragment()
            fragment.setKeyBoardEventListener(keyboardEventListener)
            return fragment
        }
    }
}