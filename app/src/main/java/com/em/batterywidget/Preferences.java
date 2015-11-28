/*
 *  Copyright 2015 Erkan Molla
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.em.batterywidget;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private SharedPreferences sharedPreferences;

    public Preferences(String preference, Context context) {
        sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
    }

    public void setValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public int getValue(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public boolean getValue(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    public String getValue(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

}
