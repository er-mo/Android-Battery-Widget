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

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class UpdateService extends IntentService {

    public static final String ACTION_BATTERY_CHANGED = "com.em.batterywidget.action.BATTERY_CHANGED";
    public static final String ACTION_BATTERY_LOW = "com.em.batterywidget.action.BATTERY_LOW";
    public static final String ACTION_BATTERY_OKAY = "com.em.batterywidget.action.BATTERY_OKAY";
    public static final String ACTION_WIDGET_UPDATE = "com.em.batterywidget.action.WIDGET_UPDATE";

    public static final String EXTRA_WIDGET_IDS = "com.em.batterywidget.extra.WIDGET_IDS";

    /**
     * Creates an UpdateService.
     */
    public UpdateService() {
        super("UpdateService");
    }

    /**
     * @param intent
     * @see IntentService
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BATTERY_CHANGED.equals(action)) {
                BatteryInfo newBatteryInfo = new BatteryInfo(intent);

                final int level = newBatteryInfo.getLevel();
                final boolean isCharging = newBatteryInfo.isCharging();
                RemoteViews remoteViews = createRemoteViews(level, isCharging);
                ComponentName componentName = new ComponentName(this, BatteryWidget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                appWidgetManager.updateAppWidget(componentName, remoteViews);

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(this);
                BatteryInfo oldBatteryInfo = new BatteryInfo(sharedPreferences);
                if (oldBatteryInfo.getLevel() != newBatteryInfo.getLevel()) {
                    Database database = new Database(this);
                    database.openWrite().insert(new DatabaseEntry(newBatteryInfo.getLevel()));
                    database.close();
                }

                newBatteryInfo.saveToSharedPreferences(sharedPreferences);
            } else if (ACTION_BATTERY_LOW.equals(action)) {
                // TODO
            } else if (ACTION_BATTERY_OKAY.equals(action)) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1500);
            } else if (ACTION_WIDGET_UPDATE.equals(action)) {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(this);
                BatteryInfo batteryInfo = new BatteryInfo(sharedPreferences);
                final int level = batteryInfo.getLevel();
                final boolean isCharging = batteryInfo.isCharging();
                RemoteViews remoteViews = createRemoteViews(level, isCharging);
                final int[] widgetIds = intent.getIntArrayExtra(EXTRA_WIDGET_IDS);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                appWidgetManager.updateAppWidget(widgetIds, remoteViews);
            }
        }
    }

    /**
     * Creates the RemoteViews object to be shown for the widget view.
     *
     * @param level      battery level
     * @param isCharging whether the battery has been charging
     * @return the RemoteViews
     */
    private RemoteViews createRemoteViews(final int level, final boolean isCharging) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_view);
        remoteViews.setImageViewResource(R.id.battery_view, R.drawable.battery);
        remoteViews.setViewVisibility(R.id.percent100, (level <= 100 && level > 90) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent90, (level <= 90 && level > 80) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent80, (level <= 80 && level > 70) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent70, (level <= 70 && level > 60) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent60, (level <= 60 && level > 50) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent50, (level <= 50 && level > 40) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent40, (level <= 40 && level > 30) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent30, (level <= 30 && level > 20) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent20, (level <= 20 && level > 10) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.percent10, (level <= 10 && level > 0) ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.charge_view, isCharging ?
                View.VISIBLE : View.INVISIBLE);
        remoteViews.setTextViewText(R.id.batterytext, String.valueOf(level) + "%");
        Intent activityIntent = new Intent(this, WidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE );
        remoteViews.setOnClickPendingIntent(R.id.widget_view, pendingIntent);
        return remoteViews;
    }

}
