package com.example.nixzan.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SenhaData {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public SenhaData(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void saveSenha(String senha) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SENHA_NAME, senha);
        database.insert(DBHelper.TABLE_SENHA, null, values);
    }
}
