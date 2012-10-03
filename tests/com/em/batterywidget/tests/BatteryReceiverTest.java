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

package com.em.batterywidget.tests;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.content.Intent;
import android.os.BatteryManager;

import com.em.batterywidget.BatteryReceiver;
import com.em.batterywidget.Constants;


public class BatteryReceiverTest extends AndroidTestCase {

    private BatteryReceiver         mReceiver;
    private BatteryInfoContext      mContext;
    private Intent                  mIntentBatteryChanged;
    private Intent                  mIntentBatteryLow;
    private Intent                  mReceivedIntent;
    
    private static final int        STATUS       =   BatteryManager.BATTERY_STATUS_CHARGING;
    private static final int        PLUG         =   BatteryManager.BATTERY_PLUGGED_USB;
    private static final int        HEALTH       =   BatteryManager.BATTERY_HEALTH_GOOD;
    private static final int        LEVEL        =   10;
    private static final int        SCALE        =   100;
    private static final int        VOLTAGE      =   1000;
    private static final int        TEMPERATURE  =   10;
    private static final String     TECHNOLOGY   =   "Lion";

    
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mReceiver              =  new  BatteryReceiver();
        mContext               =  new  BatteryInfoContext();
        mIntentBatteryChanged  =  new  Intent(Intent.ACTION_BATTERY_CHANGED);
        mIntentBatteryLow      =  new  Intent(Intent.ACTION_BATTERY_LOW);
    }
    

    @MediumTest
    public void testOnReceiveBatteryChanged() {
        
        mIntentBatteryChanged.putExtra(Constants.STATUS,      STATUS);
        mIntentBatteryChanged.putExtra(Constants.PLUG,        PLUG);
        mIntentBatteryChanged.putExtra(Constants.LEVEL,       LEVEL);
        mIntentBatteryChanged.putExtra(Constants.SCALE,       SCALE);
        mIntentBatteryChanged.putExtra(Constants.VOLTAGE,     VOLTAGE);
        mIntentBatteryChanged.putExtra(Constants.TEMPERATURE, TEMPERATURE);
        mIntentBatteryChanged.putExtra(Constants.TECHNOLOGY,  TECHNOLOGY);
        mIntentBatteryChanged.putExtra(Constants.HEALTH,      HEALTH);
        
        mContext.sendBroadcast(mIntentBatteryChanged);

        /* test received intent */
        mReceiver.onReceive(mContext, mIntentBatteryChanged);
        
        mReceivedIntent = mContext.getReceivedIntents().get(0);
        
        assertNotNull(mReceivedIntent.getAction());
        
        assertEquals(mIntentBatteryChanged, mReceivedIntent);
                
        assertEquals(STATUS,      mReceivedIntent.getIntExtra(Constants.STATUS,      0));
        assertEquals(PLUG,        mReceivedIntent.getIntExtra(Constants.PLUG,        0));
        assertEquals(LEVEL,       mReceivedIntent.getIntExtra(Constants.LEVEL,       0));
        assertEquals(SCALE,       mReceivedIntent.getIntExtra(Constants.SCALE,       0));
        assertEquals(VOLTAGE,     mReceivedIntent.getIntExtra(Constants.VOLTAGE,     0));
        assertEquals(TEMPERATURE, mReceivedIntent.getIntExtra(Constants.TEMPERATURE, 0));
        assertEquals(TECHNOLOGY,  mReceivedIntent.getStringExtra(Constants.TECHNOLOGY ));
        assertEquals(HEALTH,      mReceivedIntent.getIntExtra(Constants.HEALTH,      0));
    }
    
    
    @MediumTest
    public void testOnReceiveBatteryLow() {
        
        mIntentBatteryLow.putExtra(Constants.LEVEL, LEVEL);
        
        mContext.sendBroadcast(mIntentBatteryLow);
        
        /* test received intent */
        mReceiver.onReceive(mContext, mIntentBatteryLow);
        
        mReceivedIntent = mContext.getReceivedIntents().get(0);
        
        assertNotNull(mReceivedIntent.getAction());
        assertEquals(mIntentBatteryLow, mReceivedIntent);
        assertEquals(LEVEL, mReceivedIntent.getIntExtra(Constants.LEVEL, 0));
    }

}

