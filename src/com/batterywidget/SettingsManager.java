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

package com.batterywidget;

import com.batterywidget.storage.Preferences;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference;


public class SettingsManager extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
	
	private CheckBoxPreference   mVibrationCheckBox;
	private CheckBoxPreference   mSoundCheckBox;
	private ListPreference	     mColourList;
	
	private Preferences          mSettings;
	
	@Override
	protected void onCreate(Bundle bundle){
		
		super.onCreate(bundle);
		
		addPreferencesFromResource(R.xml.settings);
		
		buildSettings();
	}

	
	@Override
	public boolean onPreferenceChange(Preference preference, Object value) {
		
		if(preference.equals(mColourList))
			mSettings.setValue(Constants.COLOUR_SETTINGS, mColourList.getValue());
		
		return true;
	}
	
	
	private void buildSettings(){
		
		mVibrationCheckBox = (CheckBoxPreference) findPreference(Constants.VIBRATION_CHECKBOX_KEY);
		mSoundCheckBox     = (CheckBoxPreference) findPreference(Constants.SOUND_CHECKBOX_KEY);
		mColourList        = (ListPreference)     findPreference(Constants.COLOUR_LIST_KEY);
		
		mSettings  =  new Preferences(Constants.BATTERY_SETTINGS, this);
		
		mVibrationCheckBox.setChecked(mSettings.getValue(Constants.VIBRATION_SETTINGS, false));
		mSoundCheckBox.setChecked(mSettings.getValue(Constants.SOUND_SETTINGS, false));
		mColourList.setOnPreferenceChangeListener(this);
		
	}
	
	/*
	 * save state and update the widget
	 * @see android.app.Activity#onPause()
	 */
	@Override 
	public void onPause(){
		super.onPause();
		
		mSettings.setValue(Constants.VIBRATION_SETTINGS, mVibrationCheckBox.isChecked());
		mSettings.setValue(Constants.SOUND_SETTINGS, mSoundCheckBox.isChecked());
		mSettings.setValue(Constants.COLOUR_SETTINGS, mColourList.getValue());
		
		getApplicationContext().startService(new Intent(getApplicationContext(), BatteryUpdateService.class));
	}

}


