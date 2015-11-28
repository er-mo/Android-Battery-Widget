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

import java.util.Date;

public class DatabaseEntry {

    private long time;
    private int level;

    public DatabaseEntry(int level) {
        this.time = new Date().getTime();
        this.level = level;
    }

    public DatabaseEntry(long time, int level) {
        this.time = time;
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public int getLevel() {
        return level;
    }
}
