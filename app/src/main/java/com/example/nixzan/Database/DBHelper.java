package com.example.nixzan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "finances.db";
    private static final int DATABASE_VERSION = 2;  // Aumenta a versão do banco para refletir a mudança

    // Nome das tabelas
    public static final String TABLE_USER = "user";
    public static final String TABLE_REVENUE = "revenue";
    public static final String TABLE_EXPENSE = "expense";

    // Colunas da tabela "user"
    public static final String COLUMN_USER_NAME = "name";

    // Colunas da tabela "revenue"
    public static final String COLUMN_REVENUE_DATE = "date";
    public static final String COLUMN_REVENUE_AMOUNT = "amount";
    public static final String COLUMN_REVENUE_FROM = "from_person";

    // Colunas da tabela "expense"
    public static final String COLUMN_EXPENSE_DATE = "date";
    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "description";  // Descrição da despesa
    public static final String COLUMN_EXPENSE_CATEGORY = "category";  // Categoria da despesa

    // SQL para criar as tabelas
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_USER_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_REVENUE = "CREATE TABLE " + TABLE_REVENUE + " (" +
            COLUMN_REVENUE_DATE + " TEXT NOT NULL, " +
            COLUMN_REVENUE_AMOUNT + " REAL NOT NULL, " +
            COLUMN_REVENUE_FROM + " TEXT);";

    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + " (" +
            COLUMN_EXPENSE_DATE + " TEXT NOT NULL, " +
            COLUMN_EXPENSE_AMOUNT + " REAL NOT NULL, " +
            COLUMN_EXPENSE_DESCRIPTION + " TEXT, " +  // Descrição da despesa
            COLUMN_EXPENSE_CATEGORY + " TEXT);";  // Categoria da despesa

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REVENUE);
        db.execSQL(CREATE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Fazendo a atualização, caso a versão do banco de dados seja maior
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_EXPENSE + " ADD COLUMN " + COLUMN_EXPENSE_DESCRIPTION + " TEXT;");
            db.execSQL("ALTER TABLE " + TABLE_EXPENSE + " ADD COLUMN " + COLUMN_EXPENSE_CATEGORY + " TEXT;");
        }
    }
}
