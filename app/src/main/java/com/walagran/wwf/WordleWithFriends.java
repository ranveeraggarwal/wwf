package com.walagran.wwf;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class WordleWithFriends extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
