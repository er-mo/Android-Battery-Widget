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
import com.em.batterywidget.BatteryWidgetActivity;
import com.em.batterywidget.R;

public class BatteryWidgetActivityTest extends ActivityInstrumentationTestCase2<BatteryWidgetActivity> {

    public BatteryWidgetActivityTest() {
        super(BatteryWidgetActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        BatteryWidgetActivity activity = getActivity();
        assertNotNull(activity);
        assertNotNull(activity.getWindow().getDecorView());
    }

    @MediumTest
    public void testLifeCycle() {
        BatteryWidgetActivity activity = getActivity();
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);
        getInstrumentation().callActivityOnPause(activity);
        getInstrumentation().callActivityOnStop(activity);
        getInstrumentation().callActivityOnDestroy(activity);
    }

    @MediumTest
    public void testCurrentView() {
        BatteryWidgetActivity activity = getActivity();
        assertEquals(View.VISIBLE, activity.findViewById(R.id.state).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.plug).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.level).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.scale).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.voltage).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.temperature).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.technology).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.health).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.summaryButton).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.settingsButton).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.historyButton).getVisibility());
    }

}
