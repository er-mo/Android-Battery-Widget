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

package com.kokteil.packages.batterywidget;

import android.app.Activity;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


public class OnWidgetClick extends Activity{
	
    private TextView 	mStatusView;
    private TextView	mPlugView;
    private TextView 	mLevelView;
    private TextView 	mScaleView;
    private TextView	mVoltageView;
    private TextView	mTemperatureView;
    private TextView	mTechnologyView;
    private TextView	mHealthView;

    private Preferences 	mBatteryInfo;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                             WindowManager.LayoutParams.FLAG_BLUR_BEHIND);  

        setContentView(R.layout.battery_info_view);

        mStatusView      =  (TextView) findViewById(R.id.state);
        mPlugView        =  (TextView) findViewById(R.id.plug);
        mLevelView       =  (TextView) findViewById(R.id.level);
        mScaleView       =  (TextView) findViewById(R.id.scale);
        mVoltageView     =  (TextView) findViewById(R.id.voltage);
        mTemperatureView =  (TextView) findViewById(R.id.temperature);
        mTechnologyView  =  (TextView) findViewById(R.id.technology);
        mHealthView      =  (TextView) findViewById(R.id.health);

        updateBatteryInfoView();		

    }

    public void updateBatteryInfoView(){
    
        try {

            mBatteryInfo = new Preferences(Constants.BATTERY_INFO, getApplicationContext());

            mStatusView.setText(getBatteryStatus());
            mPlugView.setText(getBatteryPlug());
            mLevelView.setText(mBatteryInfo.getValue(Constants.LEVEL, 0)+
                                        this.getString(R.string.batteryLevelSymbol));
            mScaleView.setText(mBatteryInfo.getValue(Constants.SCALE, 0)+
                                        this.getString(R.string.Empty));
            mVoltageView.setText(mBatteryInfo.getValue(Constants.VOLTAGE, 0)+
                                        this.getString(R.string.batteryVoltSymbol));
            mTemperatureView.setText(getBatteryTemperature()+
                                        this.getString(R.string.batteryCelsiusSymbol));
            mTechnologyView.setText(mBatteryInfo.getValue(Constants.TECHNOLOGY,"")+
                                        this.getString(R.string.Empty));
            mHealthView.setText(getBatteryHealth());

        }catch (Exception e) {}

    }


    private int getBatteryStatus(){

        int status = mBatteryInfo.getValue(Constants.STATUS, 0);
        switch (status){

            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return R.string.Unknown;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return R.string.batteryStatusCharging;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return R.string.batteryStatusDischarging;
            case BatteryManager.BATTERY_STATUS_FULL:
                return R.string.batteryStatusFull;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return R.string.batteryStatusNotCharging;
            default:
                return R.string.Unknown;
        }
    }

	
    private int getBatteryPlug(){

        int plug = mBatteryInfo.getValue(Constants.PLUG, 0);
        switch (plug) {

            case BatteryManager.BATTERY_PLUGGED_AC:
                return R.string.batteryPlugAC;
            case BatteryManager.BATTERY_PLUGGED_USB:
                return R.string.batteryPlugUSB;
            default:
                return R.string.batteryPlugNone;
        }
    }

    private int getBatteryHealth(){

        int health = mBatteryInfo.getValue(Constants.HEALTH, 0);
        switch (health){

            case BatteryManager.BATTERY_HEALTH_DEAD:
                return R.string.batteryHealthDead;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return R.string.batteryHealthGood;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return R.string.batteryHealthOverVoltage;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return R.string.batteryHealthOverheat;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return R.string.batteryHealthUnspecifiedFailure;
            default:
                return R.string.Unknown;
        }
    }

	
    private int getBatteryTemperature(){
        int temperature = mBatteryInfo.getValue(Constants.TEMPERATURE, 0);
        temperature = temperature / 10;
        return temperature;
    }

}

