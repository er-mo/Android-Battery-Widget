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

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;


public class BatteryUpdateService extends Service {

    private BatteryReceiver  mBatteryReceiver = null;
    private IntentFilter     mIntentFilter;

    private int  batteryLevel;
    private int  batteryStatus;

    @Override
    public void onStart(Intent intent, int id){

        if (mBatteryReceiver == null)
            registerNewReceiver();

        Preferences mBatteryInfo  = new Preferences(Constants.BATTERY_INFO, this);
        
        batteryLevel   =  mBatteryInfo.getValue(Constants.LEVEL, 0);
        batteryStatus  =  mBatteryInfo.getValue(Constants.STATUS, 
                                            BatteryManager.BATTERY_STATUS_UNKNOWN);

        RemoteViews widgetView = getWidgetRemoteView();
        ComponentName widgetComponent = new ComponentName(this, BatteryWidget.class);

        if(widgetView != null && widgetComponent != null){
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            if(manager != null)
                manager.updateAppWidget(widgetComponent, widgetView);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatteryReceiver);
    }   


    private void registerNewReceiver(){
        mBatteryReceiver = new BatteryReceiver();
        mIntentFilter 	 = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryReceiver, mIntentFilter);
    }


    private RemoteViews getWidgetRemoteView(){

        RemoteViews widgetView = new RemoteViews(this.getPackageName(), R.layout.widget_view);

        widgetView.setViewVisibility(R.id.batterytext, View.VISIBLE);
        widgetView.setViewVisibility(R.id.percent100, isBetweenOf(100)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent90, isBetweenOf(90)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent80, isBetweenOf(80)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent70, isBetweenOf(70)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent60, isBetweenOf(60)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent50, isBetweenOf(50)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent40, isBetweenOf(40)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent30, isBetweenOf(30)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent20, isBetweenOf(20)? View.VISIBLE:View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent10, batteryLevel <= 10? View.VISIBLE:View.INVISIBLE);	

        widgetView.setImageViewResource(R.id.battery_view, R.drawable.battery);

        widgetView.setViewVisibility(R.id.charge_view, batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING? 
                                                                                 View.VISIBLE:View.INVISIBLE);

        widgetView.setTextViewText(R.id.batterytext, String.valueOf(batteryLevel)+"%");

        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, 
                                                 new Intent(this, OnWidgetClick.class), 0);
        widgetView.setOnClickPendingIntent(R.id.widget_view, mPendingIntent);

        return widgetView;
    }

    private boolean isBetweenOf(int percent){
        int minus = percent - 10;
        if (batteryLevel <= percent && batteryLevel > minus)
            return true;
        else
            return false;
    }

}



