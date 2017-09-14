/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muilat.j_developers.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.muilat.j_developers.R;

public class JDeveloperPreferences extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener{


    /**
     * @param context Context used to get the SharedPreferences
     */
    public static String getPreferredDeveloperLocation(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForLocation = context.getString(R.string.settings_location_key);
        String defaultLocation = context.getString(R.string.settings_location_defaultvalue);

        return prefs.getString(keyForLocation, defaultLocation);
    }

    /**
     * @param context Context used to get the SharedPreferences
     */
    public static String getPreferredDeveloperLanguage(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForLanguage = context.getString(R.string.settings_language_key);
        String defaultLanguage = context.getString(R.string.settings_language_defaultvalue);

        return prefs.getString(keyForLanguage, defaultLanguage);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}