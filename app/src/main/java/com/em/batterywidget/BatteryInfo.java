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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;

public class BatteryInfo {

    public static final String EXTRA_STATUS = BatteryManager.EXTRA_STATUS;
    public static final String EXTRA_HEALTH = BatteryManager.EXTRA_HEALTH;
    public static final String EXTRA_PRESENT = BatteryManager.EXTRA_PRESENT;
    public static final String EXTRA_LEVEL = BatteryManager.EXTRA_LEVEL;
    public static final String EXTRA_SCALE = BatteryManager.EXTRA_SCALE;
    public static final String EXTRA_ICON_SMALL = BatteryManager.EXTRA_ICON_SMALL;
    public static final String EXTRA_PLUGGED = BatteryManager.EXTRA_PLUGGED;
    public static final String EXTRA_VOLTAGE = BatteryManager.EXTRA_VOLTAGE;
    public static final String EXTRA_TEMPERATURE = BatteryManager.EXTRA_TEMPERATURE;
    public static final String EXTRA_TECHNOLOGY = BatteryManager.EXTRA_TECHNOLOGY;

    private int status;
    private int health;
    private boolean present;
    private int level;
    private int scale;
    private int iconSmallResId;
    private int plugged;
    private int voltage;
    private int temperature;
    private String technology;

    private BatteryInfo() {
    }

    public BatteryInfo(final Intent intent) {
        status = intent.getIntExtra(EXTRA_STATUS, 0);
        health = intent.getIntExtra(EXTRA_HEALTH, 0);
        present = intent.getBooleanExtra(EXTRA_PRESENT, false);
        level = intent.getIntExtra(EXTRA_LEVEL, 0);
        scale = intent.getIntExtra(EXTRA_SCALE, 0);
        iconSmallResId = intent.getIntExtra(EXTRA_ICON_SMALL, 0);
        plugged = intent.getIntExtra(EXTRA_PLUGGED, 0);
        voltage = intent.getIntExtra(EXTRA_VOLTAGE, 0);
        temperature = intent.getIntExtra(EXTRA_TEMPERATURE, 0);
        technology = intent.getStringExtra(EXTRA_TECHNOLOGY);
    }

    public BatteryInfo(final SharedPreferences sharedPreferences) {
        status = sharedPreferences.getInt(EXTRA_STATUS, 0);
        health = sharedPreferences.getInt(EXTRA_HEALTH, 0);
        present = sharedPreferences.getBoolean(EXTRA_PRESENT, false);
        level = sharedPreferences.getInt(EXTRA_LEVEL, 0);
        scale = sharedPreferences.getInt(EXTRA_SCALE, 0);
        iconSmallResId = sharedPreferences.getInt(EXTRA_ICON_SMALL, 0);
        plugged = sharedPreferences.getInt(EXTRA_PLUGGED, 0);
        voltage = sharedPreferences.getInt(EXTRA_VOLTAGE, 0);
        temperature = sharedPreferences.getInt(EXTRA_TEMPERATURE, 0);
        technology = sharedPreferences.getString(EXTRA_TECHNOLOGY, "Unknown");
    }

    public void saveToIntent(final Intent intent) {
        intent.putExtra(EXTRA_STATUS, status);
        intent.putExtra(EXTRA_HEALTH, health);
        intent.putExtra(EXTRA_PRESENT, present);
        intent.putExtra(EXTRA_LEVEL, level);
        intent.putExtra(EXTRA_SCALE, scale);
        intent.putExtra(EXTRA_ICON_SMALL, iconSmallResId);
        intent.putExtra(EXTRA_PLUGGED, plugged);
        intent.putExtra(EXTRA_VOLTAGE, voltage);
        intent.putExtra(EXTRA_TEMPERATURE, temperature);
        intent.putExtra(EXTRA_TECHNOLOGY, technology);
    }

    public void saveToSharedPreferences(final SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(EXTRA_STATUS, status);
        editor.putInt(EXTRA_HEALTH, health);
        editor.putBoolean(EXTRA_PRESENT, present);
        editor.putInt(EXTRA_LEVEL, level);
        editor.putInt(EXTRA_SCALE, scale);
        editor.putInt(EXTRA_ICON_SMALL, iconSmallResId);
        editor.putInt(EXTRA_PLUGGED, plugged);
        editor.putInt(EXTRA_VOLTAGE, voltage);
        editor.putInt(EXTRA_TEMPERATURE, temperature);
        editor.putString(EXTRA_TECHNOLOGY, technology);
        editor.commit();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCharging() {
        return (this.status == BatteryManager.BATTERY_STATUS_CHARGING);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getIconSmallResId() {
        return iconSmallResId;
    }

    public void setIconSmallResId(int iconSmallResId) {
        this.iconSmallResId = iconSmallResId;
    }

    public int getPlugged() {
        return plugged;
    }

    public void setPlugged(int plugged) {
        this.plugged = plugged;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

}
