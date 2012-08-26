/*
 *  Copyright 2012 Erkan Molla
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

import com.em.batterywidget.preferences.Preferences;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;


public class OptionsManager extends PreferenceActivity implements OnSharedPreferenceChangeListener{
    
    
    private CheckBoxPreference mVibrationCB;
    private CheckBoxPreference mSoundCB;
    private CheckBoxPreference mNotifyCB;
    private ListPreference     mThemeList;
    private ListPreference     mColourList;
    private ListPreference     mTempList;
    private Preference         mAboutPref;
    private Preferences        mPrefSettings;
    private boolean            mPreferencesChanged = false;
    
    
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        addPreferencesFromResource(R.xml.options);
        
        mVibrationCB = (CheckBoxPreference) getPreferenceScreen().findPreference(
                                                 Constants.VIBRATION_CHECKBOX_KEY);
        mSoundCB     = (CheckBoxPreference) getPreferenceScreen().findPreference(
                                                 Constants.SOUND_CHECKBOX_KEY);
        mNotifyCB    = (CheckBoxPreference) getPreferenceScreen().findPreference(
                                                 Constants.NOTIFY_ICON_CHECKBOX_KEY);
        mThemeList   = (ListPreference) getPreferenceScreen().findPreference(
                                                 Constants.WIDGET_THEME_KEY);
        mColourList  = (ListPreference) getPreferenceScreen().findPreference(
                                                 Constants.TEXT_COLOUR_KEY);
        mTempList    = (ListPreference) getPreferenceScreen().findPreference(
                                                 Constants.TEMPERATURE_TYPE_KEY);
        mAboutPref   = (Preference) getPreferenceScreen().findPreference(
                                                 Constants.ABOUT_PREF_KEY);
        
        mPrefSettings = new Preferences(Constants.BATTERY_SETTINGS, this);
    }
    
    
    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();
        
        mVibrationCB.setChecked(mPrefSettings.getValue(Constants.VIBRATION_SETTINGS, false));
        mSoundCB.setChecked    (mPrefSettings.getValue(Constants.SOUND_SETTINGS, false));
        mNotifyCB.setChecked   (mPrefSettings.getValue(Constants.NOTIFY_ICON_SETTINGS, false));
        mAboutPref.setOnPreferenceClickListener(clickListener);
        
        getPreferenceScreen().getSharedPreferences()
                             .registerOnSharedPreferenceChangeListener(this);
    }
    
    
    @SuppressWarnings("deprecation")
    @Override
    public void onPause() {
        super.onPause();
        
        mPrefSettings.setValue(Constants.VIBRATION_SETTINGS, mVibrationCB.isChecked());
        mPrefSettings.setValue(Constants.SOUND_SETTINGS, mSoundCB.isChecked());
        mPrefSettings.setValue(Constants.NOTIFY_ICON_SETTINGS, mNotifyCB.isChecked());
        mPrefSettings.setValue(Constants.WIDGET_THEME_SETTINGS, mThemeList.getValue());
        mPrefSettings.setValue(Constants.TEXT_COLOUR_SETTINGS, mColourList.getValue());
        mPrefSettings.setValue(Constants.TEMPERATURE_SETTINGS, mTempList.getValue());
        
        Intent intent = new Intent(getApplicationContext(), BatteryUpdateService.class);
        getApplicationContext().startService(intent);
        
        getPreferenceScreen().getSharedPreferences()
                             .unregisterOnSharedPreferenceChangeListener(this);
    }
    
    
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        
        mPreferencesChanged = true;
    }

    
    final private OnPreferenceClickListener clickListener = new OnPreferenceClickListener() {                                                     
        public boolean onPreferenceClick(Preference preference) {

            if (preference.equals(mAboutPref)) {
                        
                Intent intent = new Intent(getApplicationContext(),AboutInfoActivity.class);                                                      
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getApplicationContext().startActivity(intent);                                                      
            }
                                                      
            return false;    
        }                                                      
    };
    
}
