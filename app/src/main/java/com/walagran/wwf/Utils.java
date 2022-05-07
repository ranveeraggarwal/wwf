package com.walagran.wwf;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Utils {
    public static @ColorInt
    int getColorFromAttribute(Context context, @AttrRes int attributeId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attributeId, typedValue, true);
        return typedValue.data;
    }

    public static Boolean isWordValid(Context context, String word) {
        AssetManager assetManager = context.getAssets();
        InputStream wordList;
        String wordLine = "";
        try {
            wordList = assetManager.open("words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(wordList));
            wordLine = reader.readLine();
            wordList.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordLine.split(",")));
        return words.contains(word.toLowerCase(Locale.ENGLISH));
    }
}
