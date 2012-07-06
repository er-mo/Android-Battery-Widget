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

import com.batterywidget.storage.SQLiteDataBase;
import com.batterywidget.storage.Preferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Vibrator;


public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    	try{
    		/* 
    		 */
	        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
	        	
	        	Preferences batteryInfo  =  new Preferences(Constants.BATTERY_INFO, context);
	        	
	        	if (intent.getIntExtra(Constants.LEVEL, 0) != batteryInfo.getValue(Constants.LEVEL, 0)){
	        		
	        		SQLiteDataBase.Entry entry = new SQLiteDataBase.Entry(intent.getIntExtra(Constants.LEVEL, 0));
	        		SQLiteDataBase db = new SQLiteDataBase(context);
	        		db.openWrite();
	        		db.insertEntry(entry);
	        		db.close();
	        		
	        	}
	        	
	        	batteryInfo.setValue(Constants.STATUS, intent.getIntExtra(Constants.STATUS, 
	        			                           BatteryManager.BATTERY_STATUS_UNKNOWN));
	        	batteryInfo.setValue(Constants.PLUG, intent.getIntExtra(Constants.PLUG, 0));
	        	batteryInfo.setValue(Constants.LEVEL, intent.getIntExtra(Constants.LEVEL, 0));
	        	batteryInfo.setValue(Constants.SCALE, intent.getIntExtra(Constants.SCALE, 0));
	        	batteryInfo.setValue(Constants.VOLTAGE, intent.getIntExtra(Constants.VOLTAGE, 0));
	        	batteryInfo.setValue(Constants.TEMPERATURE, intent.getIntExtra(Constants.TEMPERATURE, 0));
	        	batteryInfo.setValue(Constants.TECHNOLOGY, intent.getStringExtra(Constants.TECHNOLOGY));
	        	batteryInfo.setValue(Constants.HEALTH, intent.getIntExtra(Constants.HEALTH, 
	        			                           BatteryManager.BATTERY_HEALTH_UNKNOWN));
	        	
	        	context.startService(new Intent(context, BatteryUpdateService.class));
	        }
	        
	        
	        /* 
	         */
	        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
	        	
	        	Preferences mPreference = new Preferences(Constants.BATTERY_SETTINGS, context);
	        	
	        	if (mPreference.getValue(Constants.VIBRATION_SETTINGS, false)){
	        		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	        		new Thread(){
	        			@Override
	        			public void run(){
	        				vibrator.vibrate(1000);
	        			}
	        		}.start();
	        	}
	        	
	        	if (mPreference.getValue(Constants.SOUND_SETTINGS, false)){
	        		final MediaPlayer mp = MediaPlayer.create(context, R.raw.low_battery);
	        		new Thread(){
	        			@Override
	        			public void run(){
	        				mp.start();
	        			}
	        		}.start();
	        	}
	        }
	        
	        
    	} catch (Exception e){}
    }

}
