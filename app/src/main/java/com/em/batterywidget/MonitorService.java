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

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

/**
 * This service is used to monitor the battery information.
 */
public class MonitorService extends Service {

    final private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                BatteryInfo batteryInfo = new BatteryInfo(intent);
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.setAction(UpdateService.ACTION_BATTERY_CHANGED);
                batteryInfo.saveToIntent(updateIntent);
                context.startService(updateIntent);
            } else if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.setAction(UpdateService.ACTION_BATTERY_LOW);
                context.startService(updateIntent);
            } else if (Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())) {
                Intent updateIntent = new Intent(context, UpdateService.class);
                updateIntent.setAction(UpdateService.ACTION_BATTERY_OKAY);
                context.startService(updateIntent);
            }
        }
    };

    /**
     * Creates the MonitorService.
     */
    public MonitorService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return Service.START_STICKY;
    }

}
