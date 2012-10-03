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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.RemoteViews;


public class NotificationsManager {

    private static final int    _ID = 123;
    private NotificationManager notificationManager;
    private Context             mContext;

    
    public NotificationsManager(Context context) {

        mContext            =  context;
        notificationManager =  (NotificationManager) mContext
                               .getSystemService(Context.NOTIFICATION_SERVICE);
    }
    

    @SuppressWarnings("deprecation")
    public void updateNotificationIcon(Preferences prefSettings, Preferences prefBatteryInfo) {
        
        boolean activated = prefSettings.getValue(Constants.NOTIFY_ICON_SETTINGS, false);
        
        if (!activated) {
            if (notificationManager != null) {
                notificationManager.cancel(_ID);
            }
            return;
        }

        int  status = prefBatteryInfo.getValue(Constants.STATUS, 0);
        int  level  = prefBatteryInfo.getValue(Constants.LEVEL, 0);
        int  plug   = prefBatteryInfo.getValue(Constants.PLUG, 0);
        
        int  icon   = status == BatteryManager.BATTERY_STATUS_CHARGING 
                              ? R.drawable.ic_stat_charge : 
                                R.drawable.ic_stat_battery_level;

        String title  = mContext.getString(R.string.app_name);
        Intent intent = new Intent(mContext, BatteryWidgetActivity.class);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        RemoteViews   contentView   = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        
        contentView.setImageViewResource(R.id.notificationImage,  R.drawable.launcher);
        contentView.setTextViewText     (R.id.notificationTitle,  title);
        contentView.setTextViewText     (R.id.notificationStatus, mContext.getString(R.string.textStatus)+ 
                                                                           getBatteryStatus(status, plug));

        Notification notification  =  new Notification(icon, null, System.currentTimeMillis());
        notification.contentView   =  contentView;
        notification.contentIntent =  pendingIntent;
        notification.tickerText    =  null;
        notification.iconLevel     =  level;
        notification.flags         =  Notification.FLAG_ONGOING_EVENT
                                   |  Notification.FLAG_ONLY_ALERT_ONCE
                                   |  Notification.FLAG_NO_CLEAR 
                                   |  Notification.FLAG_SHOW_LIGHTS;

        notificationManager.notify(_ID, notification);
    }
    

    private String getBatteryStatus(int status, int plug) {

        switch (status) {
                
            case BatteryManager.BATTERY_STATUS_FULL:
                return Constants.FULL;
                
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return Constants.DISCHARGING;
                
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return Constants.DISCHARGING;
                
            case BatteryManager.BATTERY_STATUS_CHARGING:
                if (plug == BatteryManager.BATTERY_PLUGGED_AC) {
                    return Constants.CHARGING_AC;
                }
                else if (plug == BatteryManager.BATTERY_PLUGGED_USB) {
                    return Constants.CHARGING_USB;
                }
                else {
                    return Constants.CHARGING;
                }
                
            default:
                return Constants.UNKNOWN;
        }
    }
    
}
