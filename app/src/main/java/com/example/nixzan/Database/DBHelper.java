package com.example.nixzan.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "finances.db";
    private static final int DATABASE_VERSION = 3;

    // Nome das tabelas
    public static final String TABLE_USER = "user";
    public static final String TABLE_TRANSACAO = "transacao";
    public static final String TABLE_SALDO = "saldo";

    // Colunas da tabela "user"
    public static final String COLUMN_USER_NAME = "name";

    // Colunas da tabela "transacao"
    public static final String COLUMN_TRANSACAO_VALOR = "valorTransacao";
    public static final String COLUMN_TRANSACAO_DESCRICAO = "descricao";
    public static final String COLUMN_TRANSACAO_METODO = "metodo";
    public static final String COLUMN_TRANSACAO_DATA = "data";
    public static final String COLUMN_TRANSACAO_DESPESA_OU_RECEITA = "despesaOuReceita";  // "despesa" ou "receita"

    // Colunas da tabela "saldo"
    public static final String COLUMN_SALDO_VALOR = "valorAtual";  // Saldo total

    // SQL para criar as tabelas
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_USER_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_TRANSACAO = "CREATE TABLE " + TABLE_TRANSACAO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TRANSACAO_VALOR + " REAL NOT NULL, " +
            COLUMN_TRANSACAO_DESCRICAO + " TEXT, " +
            COLUMN_TRANSACAO_METODO + " TEXT, " +
            COLUMN_TRANSACAO_DATA + " TEXT NOT NULL, " +
            COLUMN_TRANSACAO_DESPESA_OU_RECEITA + " TEXT NOT NULL);";  // Receita ou despesa

    private static final String CREATE_TABLE_SALDO = "CREATE TABLE " + TABLE_SALDO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SALDO_VALOR + " REAL NOT NULL);";  // Saldo atual

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TRANSACAO);
        db.execSQL(CREATE_TABLE_SALDO);

        // Inicializar o saldo com 0,00
        db.execSQL("INSERT INTO " + TABLE_SALDO + " (" + COLUMN_SALDO_VALOR + ") VALUES (0.00);");
    }
    // No DBHelper
    public void deletarTransacao(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACAO, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // Adicionando a tabela de transações
            db.execSQL(CREATE_TABLE_TRANSACAO);
            // Adicionando a tabela de saldo
            db.execSQL(CREATE_TABLE_SALDO);
            // Inicializa o saldo com 0,00
            db.execSQL("INSERT INTO " + TABLE_SALDO + " (" + COLUMN_SALDO_VALOR + ") VALUES (0.00);");
        }
    }
}
