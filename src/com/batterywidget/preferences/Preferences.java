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

package com.batterywidget.preferences;

import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {
	
	private SharedPreferences  mPreference;

    public Preferences(String preferenceKey, Context context){
    	mPreference = context.getSharedPreferences(preferenceKey, 0);
    }
    
    public void setValue(String key, int value){
    	if (mPreference != null){
    		SharedPreferences.Editor editor = mPreference.edit();
    		editor.putInt(key, value);
    		editor.commit();
    	}
    }
    
    public void setValue(String key, boolean value){
    	if (mPreference != null){
    		SharedPreferences.Editor editor = mPreference.edit();
    		editor.putBoolean(key, value);
    		editor.commit();
    	}
    }
    
    public void setValue(String key, String value){
    	if (mPreference != null){
    		SharedPreferences.Editor editor = mPreference.edit();
    		editor.putString(key, value);
    		editor.commit();
    	}
    }
    
    public int getValue(String key, int value){
    	if (mPreference != null)
    		return mPreference.getInt(key, value);
    	
    	return value;
    }
    
    public boolean getValue(String key, boolean value){
    	if (mPreference != null)
    		return mPreference.getBoolean(key, value);
    	
    	return value;
    }

    public String getValue(String key, String value){
    	if (mPreference != null)
    		return mPreference.getString(key, value);
    	
    	return value;
    }

}



