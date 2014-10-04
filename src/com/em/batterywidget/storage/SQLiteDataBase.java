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

package com.em.batterywidget.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.em.batterywidget.Constants;
import java.util.Date;

public class SQLiteDataBase {

    private SQLiteDatabase     sqLiteDataBase;
    private DataBaseOpenHelper dbOpenHelper;

    private static final int   DB_VERSION = 2;
    public static final int    _ID        = 0;
    public static final int    _TIME      = 1;
    public static final int    _LEVEL     = 2;

    /*
     * static class DataBaseOpenHelper
     */
    private static class DataBaseOpenHelper extends SQLiteOpenHelper {
        public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int id, int it) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.DB_TABLE);
            onCreate(sqLiteDatabase);
        }

        private static final String DB_SQL_CREATE = "CREATE TABLE "
                                                          + Constants.DB_TABLE
                                                          + " ("
                                                          + Constants.KEY_ID
                                                          + " integer PRIMARY KEY AUTOINCREMENT, "
                                                          + Constants.KEY_TIME
                                                          + " LONG NOT NULL, "
                                                          + Constants.KEY_LEVEL
                                                          + " INT NOT NULL);";
    }

    public static class Entry {

        public Date date;
        public int  level;

        public Entry(int level) {
            this.level = level;
            this.date = new Date();
        }
    }

    /*
     * SQLiteDataBase(Context)
     */
    public SQLiteDataBase(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context, Constants.DB_NAME, null, DB_VERSION);
    }

    public SQLiteDataBase openWrite() {
        sqLiteDataBase = dbOpenHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDataBase openRead() {
        sqLiteDataBase = dbOpenHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        sqLiteDataBase.close();
    }

    public boolean availableEntries() {
        if ((DatabaseUtils.queryNumEntries(sqLiteDataBase, Constants.DB_TABLE)) > 5)
            return true;
        else
            return false;
    }

    public long insertEntry(Entry entry) {
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TIME, entry.date.getTime());
        values.put(Constants.KEY_LEVEL, entry.level);
        return sqLiteDataBase.insert(Constants.DB_TABLE, null, values);
    }

    public Cursor getEntries() {
        return sqLiteDataBase.query(Constants.DB_TABLE, new String[] {
                Constants.KEY_ID,
                Constants.KEY_TIME,
                Constants.KEY_LEVEL
        }, Constants.KEY_TIME + " > " + (new Date().getTime() - 1000 * 60 * 60 * 24 * 7),
                null, null, null, null);
    }

}
