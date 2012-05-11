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


public class Constants {
	
	public final static String BATTERY_INFO	     =  "BATTERY_INFO_WIDGET";
	public final static String BATTERY_SETTINGS  =  "BATTERY_SETTINGS";
	
	public final static String STATUS       =  "status";	
	public final static String PLUG         =  "plugged";
	public final static String LEVEL        =  "level";
	public final static String SCALE        =  "scale";
	public final static String VOLTAGE      =  "voltage";
	public final static String TEMPERATURE  =  "temperature";
	public final static String TECHNOLOGY   =  "technology";
	public final static String HEALTH       =  "health";
	public final static String DEFAULT      =  "unknown";
	
	public final static String BATTERY_USAGE = "android.intent.action.POWER_USAGE_SUMMARY";
	
	public final static String VIBRATION_SETTINGS      =  "vibration_settings";
	public final static String SOUND_SETTINGS          =  "sound_settings"; 
	public final static String COLOUR_SETTINGS         =  "colour_settings";
	public final static String VIBRATION_CHECKBOX_KEY  =  "vibration_checkbox";
	public final static String SOUND_CHECKBOX_KEY      =  "sound_checkbox";
	public final static String COLOUR_LIST_KEY         =  "colour_list";
	
	public final static String DEFAULT_COLOUR    =  "#FFFFFF";
	
	public static final String DB_NAME   =  "batteryWidget.db";
	public static final String DB_TABLE  =  "batteryWidgetEntryTable";
	public static final String KEY_ID    =  "_id";
	public static final String KEY_TIME  =  "time";
	public static final String KEY_LEVEL =  "level";
	
	public static final String XAxisTitle = "Date";
	public static final String YAxisTitle = "Charge level";
	public static final String DateFormat = "dd MMM";
	
}
