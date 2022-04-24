package com.walagran.wwf.ui.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walagran.wwf.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KeyboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeyboardFragment extends Fragment {

    KeyboardEventListener keyboardEventListener;

    public KeyboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param keyboardEventListener Listens to the keyboard events and acts accordingly.
     * @return A new instance of fragment KeyboardFragment.
     */
    public static KeyboardFragment newInstance(KeyboardEventListener keyboardEventListener) {
        KeyboardFragment fragment = new KeyboardFragment();
        fragment.setKeyBoardEventListener(keyboardEventListener);
        return fragment;
    }

    public void setKeyBoardEventListener(KeyboardEventListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_keyboard, container, false);

        for (char alpha = 'a'; alpha <= 'z'; alpha++) {
            char passedAlphabet = alpha;
            rootView
                    .findViewById(rootView
                            .getResources()
                            .getIdentifier("key_"+alpha, "id", rootView
                                    .getContext()
                                    .getPackageName()))
                    .setOnClickListener(view -> keyboardEventListener
                            .onAlphaKeyPressed(passedAlphabet));
        }

        return rootView;
    }
}