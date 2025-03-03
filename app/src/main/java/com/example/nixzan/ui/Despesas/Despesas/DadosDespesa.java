package com.example.nixzan.ui.Despesas.Despesas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import com.example.nixzan.Database.DBHelper;

public class DadosDespesa {
    private SQLiteDatabase bancoDeDados;
    private DBHelper dbHelper;

    public DadosDespesa(Context context) {
        dbHelper = new DBHelper(context);
        bancoDeDados = dbHelper.getWritableDatabase();
    }

    public void salvarDespesa(String data, float valor, String descricao, String categoria) {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUMN_EXPENSE_DATE, data);
        valores.put(DBHelper.COLUMN_EXPENSE_AMOUNT, valor);
        valores.put(DBHelper.COLUMN_EXPENSE_DESCRIPTION, descricao); // Salva a descrição
        valores.put(DBHelper.COLUMN_EXPENSE_CATEGORY, categoria); // Salva a categoria

        bancoDeDados.insert(DBHelper.TABLE_EXPENSE, null, valores);
    }

    public List<Despesa> obterTodasDespesas() {
        List<Despesa> despesas = new ArrayList<>();
        Cursor cursor = bancoDeDados.query(DBHelper.TABLE_EXPENSE,
                new String[]{DBHelper.COLUMN_EXPENSE_DATE, DBHelper.COLUMN_EXPENSE_AMOUNT, DBHelper.COLUMN_EXPENSE_DESCRIPTION, DBHelper.COLUMN_EXPENSE_CATEGORY},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_DATE));
                float valor = cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_AMOUNT));
                String descricao = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_DESCRIPTION));
                String categoria = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_CATEGORY));

                // Adicionando a despesa na lista
                despesas.add(new Despesa(data, valor, descricao, categoria));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return despesas;
    }

    public Despesa obterDespesaPorId(long id) {
        Cursor cursor = bancoDeDados.query(DBHelper.TABLE_EXPENSE,
                new String[]{DBHelper.COLUMN_EXPENSE_DATE, DBHelper.COLUMN_EXPENSE_AMOUNT, DBHelper.COLUMN_EXPENSE_DESCRIPTION, DBHelper.COLUMN_EXPENSE_CATEGORY},
                "_id = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_DATE));
            float valor = cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_AMOUNT));
            String descricao = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_DESCRIPTION));
            String categoria = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EXPENSE_CATEGORY));

            cursor.close();
            return new Despesa(data, valor, descricao, categoria);
        }

        cursor.close();
        return null;
    }
}
