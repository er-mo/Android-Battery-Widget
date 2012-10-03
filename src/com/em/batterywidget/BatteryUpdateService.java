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

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;


public class BatteryUpdateService extends Service {

    private BatteryReceiver mBatteryReceiver = null;
    private int             level;
    private int             status;

    
    @Override
    public void onStart(Intent intent, int id) {

        if (mBatteryReceiver == null) {
            registerNewReceiver();
        }

        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        RemoteViews widgetView   = getWidgetRemoteView();

        if (this.getResources().getBoolean(R.bool.lowVersion)) {
            manager.updateAppWidget(new ComponentName(this, BatteryWidget.class), widgetView);
        }
        else {
            manager.updateAppWidget(new ComponentName(this,BatteryWidget_HC.class), widgetView);
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
    

    private void registerNewReceiver() {

        mBatteryReceiver          = new BatteryReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(mBatteryReceiver, intentFilter);
    }

    
    private RemoteViews getWidgetRemoteView() {

        Preferences prefSettings    = new Preferences(Constants.BATTERY_SETTINGS,this);
        Preferences prefBatteryInfo = new Preferences(Constants.BATTERY_INFO,this);

        level  = prefBatteryInfo.getValue(Constants.LEVEL, 0);
        status = prefBatteryInfo.getValue(Constants.STATUS, 0);

        String color   =  prefSettings.getValue(Constants.TEXT_COLOUR_SETTINGS,Constants.DEFAULT_COLOUR);
        boolean charge =  status == BatteryManager.BATTERY_STATUS_CHARGING;

        /****/
        RemoteViews widgetView = new RemoteViews(this.getPackageName(),R.layout.widget_view);

        widgetView.setImageViewResource(R.id.battery_view, R.drawable.battery);

        widgetView.setViewVisibility(R.id.percent100, round(100)? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent90,  round(90) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent80,  round(80) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent70,  round(70) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent60,  round(60) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent50,  round(50) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent40,  round(40) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent30,  round(30) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent20,  round(20) ? View.VISIBLE : View.INVISIBLE);
        widgetView.setViewVisibility(R.id.percent10,  round(10) ? View.VISIBLE : View.INVISIBLE);

        widgetView.setViewVisibility(R.id.batterytext, View.VISIBLE);
        widgetView.setTextColor     (R.id.batterytext, Color.parseColor(color));
        widgetView.setTextViewText  (R.id.batterytext, String.valueOf(level)+"%");
        widgetView.setViewVisibility(R.id.charge_view, charge ? View.VISIBLE : View.INVISIBLE);

        /****/
        NotificationsManager mNotificationsManager = new NotificationsManager(this);
        mNotificationsManager.updateNotificationIcon(prefSettings, prefBatteryInfo);

        /****/
        Intent intent = new Intent(this, BatteryWidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        widgetView.setOnClickPendingIntent(R.id.widget_view, pendingIntent);

        return widgetView;
    }

    private boolean round(int percent) {

        int minus = percent - 10;
        if (level <= percent && level > minus)
            return true;
        else
            return false;
    }

}
