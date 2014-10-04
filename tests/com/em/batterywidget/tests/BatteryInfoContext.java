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

import android.content.Intent;
import android.test.mock.MockContext;
import java.util.ArrayList;
import java.util.List;

public class BatteryInfoContext extends MockContext {

    private List<Intent> mReceivedIntents = new ArrayList<Intent>();

    @Override
    public void sendBroadcast(Intent xiIntent) {
        mReceivedIntents.add(xiIntent);
    }

    public List<Intent> getReceivedIntents() {
        return mReceivedIntents;
    }

}
