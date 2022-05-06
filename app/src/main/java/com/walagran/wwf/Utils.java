package com.walagran.wwf;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;

public class Utils {
    public static @ColorInt int getColorFromAttribute(Context context, @AttrRes int attributeId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attributeId, typedValue, true);
        return typedValue.data;
    }

    public static Boolean isWordValid(String word) {
        return word.equals("FLASH");
    }
}
