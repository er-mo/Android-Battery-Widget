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

import com.em.batterywidget.BatteryUpdateService;

import android.content.Intent;
import android.os.IBinder;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;


public class BatteryUpdateServiceTest extends ServiceTestCase<BatteryUpdateService> {

    
    public BatteryUpdateServiceTest() {
        super(BatteryUpdateService.class);
    }
 
    
     @Override                                                                                                                                     
     protected void setUp() throws Exception {
         super.setUp();    
     }
    
     
     @SmallTest
     public void testStartable() {
         
         Intent startIntent = new Intent();
         startIntent.setClass(getContext(), BatteryUpdateService.class);
         startService(startIntent);
     }
     
     
     @MediumTest
     public void testBindable() {
         
         Intent startIntent = new Intent();
         startIntent.setClass(getContext(), BatteryUpdateService.class);
         IBinder service = bindService(startIntent);
     }

}

