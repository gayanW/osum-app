/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app;

import android.content.Context;
import android.content.SharedPreferences;

import space.linuxdeveloper.osum.login.SavedData;

public class AppSavedData extends SavedData {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    /**
     * Calculated max volumes may be not accurate at the end of the month &
     * more accurate at the beginning of the month. Thus LAST_SAVED keys are used as
     * helpers to store more precise max peak/total volumes
     *      private static final String LAST_SAVED_TIME_MAX_PEAK = "lastSavedTimeMaxPeak";
     *      private static final String LAST_SAVED_TIME_MAX_TOTAL = "lastSavedTimeMaxTotal";
     */
    public AppSavedData(Context context) {
        super(context);
        mSharedPreferences = super.getSharedPreferences();
        mSharedPreferencesEditor = super.getSharedPreferencesEditor();
    }

    public String getUsernameText() {
        return mSharedPreferences.getString("username", "");
    }

    public String getPasswordText() {
        return mSharedPreferences.getString("password", "");
    }

    public boolean isAnimationOn() {
        return mSharedPreferences.getBoolean("animate", true);
    }

    public void setUsernamePassword(String username, String password) {
        mSharedPreferencesEditor.putString("username", username);
        mSharedPreferencesEditor.putString("password", password);
        mSharedPreferencesEditor.apply();
    }

    public void setAnimationOn(boolean bool) {
        mSharedPreferencesEditor.putBoolean("animate", bool);
        mSharedPreferencesEditor.apply();
    }
}
