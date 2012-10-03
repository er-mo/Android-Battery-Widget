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

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;

import com.em.batterywidget.HistoryViewManager;
import com.em.batterywidget.R;


public class HistoryViewManagerTest extends ActivityInstrumentationTestCase2<HistoryViewManager> {
    
    
    public HistoryViewManagerTest() {
        super(HistoryViewManager.class);
    }

  
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        HistoryViewManager activity = getActivity();
        
        assertNotNull(activity);
        
        assertNotNull(activity.getWindow().getDecorView());
    }

/*    
    @MediumTest
    public void testLifeCycle() {
        
        HistoryViewManager activity = getActivity();
        
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);
        getInstrumentation().callActivityOnPause(activity);
        getInstrumentation().callActivityOnStop(activity);
        getInstrumentation().callActivityOnDestroy(activity);
    }
*/    
    
    @MediumTest
    public void testCurrentView(){
        
        HistoryViewManager activity = getActivity();
        
        assertEquals(View.VISIBLE, activity.findViewById(R.id.history_view).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.historyTitle).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.chart).getVisibility());
    }
    
}

