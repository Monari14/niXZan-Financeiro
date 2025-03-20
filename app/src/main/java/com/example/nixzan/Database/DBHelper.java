package com.example.nixzan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nixzan.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela
    public static final String TABLE_SENHA = "senha";

    // Coluna da "TABLE_SENHA"
    public static final String COLUMN_SENHA_NAME = "senha";

    // SQL para criar tabelas
    private static final String CREATE_TABLE_SENHA = "CREATE TABLE " + TABLE_SENHA + " (" +
            COLUMN_SENHA_NAME + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SENHA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENHA);
        onCreate(db);
    }

    public boolean deletarSenha(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rowsAffected = db.delete(TABLE_SENHA, "_id = ?", new String[]{String.valueOf(id)});
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }
}
