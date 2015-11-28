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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class Database {

    public static final int ID = 0;
    public static final int TIME = 1;
    public static final int LEVEL = 2;
    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_LEVEL = "level";
    public static final String NAME = "batterywidget.db";
    public static final String TABLE = "batterylevelentries";
    private static final int VERSION = 3;
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseOpenHelper databaseOpenHelper;

    public Database(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, NAME, null, VERSION);
    }

    public Database openWrite() {
        sqLiteDatabase = databaseOpenHelper.getWritableDatabase();
        return this;
    }

    public Database openRead() {
        sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public long insert(DatabaseEntry entry) {
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, entry.getTime());
        values.put(KEY_LEVEL, entry.getLevel());
        return sqLiteDatabase.insert(TABLE, null, values);
    }

    public Cursor getEntries() {
        return sqLiteDatabase.query(TABLE, new String[]{
                        KEY_ID,
                        KEY_TIME,
                        KEY_LEVEL
                }, KEY_TIME + " > " + (new Date().getTime() - 1000 * 60 * 60 * 24 * 7),
                null, null, null, null);
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {
        private static final String DB_SQL_CREATE = "CREATE TABLE "
                + TABLE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TIME + " INTEGER NOT NULL, "
                + KEY_LEVEL + " INTEGER NOT NULL);";

        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int id, int it) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(sqLiteDatabase);
        }
    }

}
