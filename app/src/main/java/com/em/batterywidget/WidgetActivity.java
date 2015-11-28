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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class WidgetActivity extends Activity {

    private LinearLayout mGraphLayout;
    private GraphicalView mGraphicalView;
    private XYSeries mXYSeries;
    private TextView mStatusView;
    private TextView mPlugView;
    private TextView mLevelView;
    private TextView mScaleView;
    private TextView mVoltageView;
    private TextView mTemperatureView;
    private TextView mTechnologyView;
    private TextView mHealthView;

    final private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                BatteryInfo batteryInfo = new BatteryInfo(intent);
                updateView(batteryInfo);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        mGraphLayout = (LinearLayout) findViewById(R.id.chart);
        mStatusView = (TextView) findViewById(R.id.state);
        mPlugView = (TextView) findViewById(R.id.plug);
        mLevelView = (TextView) findViewById(R.id.level);
        mScaleView = (TextView) findViewById(R.id.scale);
        mVoltageView = (TextView) findViewById(R.id.voltage);
        mTemperatureView = (TextView) findViewById(R.id.temperature);
        mTechnologyView = (TextView) findViewById(R.id.technology);
        mHealthView = (TextView) findViewById(R.id.health);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        BatteryInfo batteryInfo = new BatteryInfo(sharedPreferences);
        updateView(batteryInfo);

        // ensure service is running
        startService(new Intent(this, MonitorService.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGraphLayout != null) {
            mGraphLayout.removeAllViews();
        }
    }

    private synchronized void updateView(BatteryInfo batteryInfo) {
        mStatusView.setText(getStatus(batteryInfo));
        mPlugView.setText(getPlug(batteryInfo));
        mTemperatureView.setText(getTemperature(batteryInfo));
        mHealthView.setText(getHealth(batteryInfo));
        mLevelView.setText(getLevel(batteryInfo));
        mScaleView.setText(getScale(batteryInfo));
        mVoltageView.setText(getVoltage(batteryInfo));
        mTechnologyView.setText(getTechnology(batteryInfo));
        drawGraph();
    }

    private void initGraphicalView() {
        if (mGraphicalView == null) {
            XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
            renderer.setYAxisMax(100);
            renderer.setYAxisMin(0);
            renderer.setAxisTitleTextSize(16);
            renderer.setChartTitleTextSize(20);
            renderer.setLabelsTextSize(15);
            renderer.setLegendTextSize(15);
            renderer.setPanEnabled(true, false);
            renderer.setMargins(new int[]{20, 30, 15, 0});
            renderer.setZoomEnabled(true, false);
            renderer.setShowGrid(true);
            renderer.setZoomButtonsVisible(false);

            XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
            mXYSeries = new XYSeries("");
            dataSet.addSeries(mXYSeries);

            XYSeriesRenderer xySeriesRenderer = new XYSeriesRenderer();
            xySeriesRenderer.setColor(Color.CYAN);
            renderer.addSeriesRenderer(xySeriesRenderer);

            mGraphicalView = ChartFactory.getTimeChartView(this, dataSet, renderer, null);
            mGraphLayout.addView(mGraphicalView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                mGraphLayout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void drawGraph() {
        initGraphicalView();
        Database database = null;
        Cursor cursor = null;
        try {
            database = new Database(this);
            cursor = database.openRead().getEntries();
            if (cursor.moveToFirst()) {
                do {
                    long time = cursor.getLong(Database.TIME);
                    int level = cursor.getInt(Database.LEVEL);
                    mXYSeries.add(time, level);
                } while (cursor.moveToNext());
            }
            mGraphicalView.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null)
                database.close();
            if (cursor != null)
                cursor.close();
        }
    }

    private String getStatus(BatteryInfo batteryInfo) {
        switch (batteryInfo.getStatus()) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return getString(R.string.batteryStatusCharging);
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return getString(R.string.batteryStatusDischarging);
            case BatteryManager.BATTERY_STATUS_FULL:
                return getString(R.string.batteryStatusFull);
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return getString(R.string.batteryStatusNotCharging);
            default:
                return getString(R.string.Unknown);
        }
    }

    private String getPlug(BatteryInfo batteryInfo) {
        switch (batteryInfo.getPlugged()) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return getString(R.string.batteryPlugAC);
            case BatteryManager.BATTERY_PLUGGED_USB:
                return getString(R.string.batteryPlugUSB);
            default:
                return getString(R.string.batteryPlugNone);
        }
    }

    private String getTemperature(BatteryInfo batteryInfo) {
        float temperature = batteryInfo.getTemperature() / (float) 10.0;
        return temperature + "\u00B0 C";
    }

    private String getHealth(BatteryInfo batteryInfo) {
        switch (batteryInfo.getHealth()) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return getString(R.string.batteryHealthDead);
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return getString(R.string.batteryHealthGood);
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return getString(R.string.batteryHealthOverVoltage);
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return getString(R.string.batteryHealthOverheat);
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return getString(R.string.batteryHealthUnspecifiedFailure);
            default:
                return getString(R.string.Unknown);
        }
    }

    private String getLevel(BatteryInfo batteryInfo) {
        return String.valueOf(batteryInfo.getLevel()) + "%";
    }

    private String getScale(BatteryInfo batteryInfo) {
        return String.valueOf(batteryInfo.getScale());
    }

    private String getVoltage(BatteryInfo batteryInfo) {
        return String.valueOf(batteryInfo.getVoltage()) + "mV";
    }

    private String getTechnology(BatteryInfo batteryInfo) {
        return batteryInfo.getTechnology();
    }

}
