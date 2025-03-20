package com.example.nixzan.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserData {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public UserData(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void saveUserName(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SENHA_NAME, name);
        database.insert(DBHelper.TABLE_SENHA, null, values);
    }
}
