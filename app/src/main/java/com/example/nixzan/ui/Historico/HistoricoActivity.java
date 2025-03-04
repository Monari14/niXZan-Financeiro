package com.example.nixzan.ui.Historico;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.Model.Transacao;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Despesas.DespesasActivity;
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    private TextView textTotalGasto, textTotalGanho;
    private ListView listView;
    private TransacaoAdapter adapter;
    private List<Transacao> transacoes;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listView = findViewById(R.id.listView);
        textTotalGasto = findViewById(R.id.textTotalGasto);
        textTotalGanho = findViewById(R.id.textTotalGanho);
        dbHelper = new DBHelper(this);

        carregarTransacoes();
        carregarTotalGanho();
        carregarTotalGasto();

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_historico);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    startActivity(new Intent(HistoricoActivity.this, DashboardActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_despesas) {
                    startActivity(new Intent(HistoricoActivity.this, DespesasActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_receitas) {
                    startActivity(new Intent(HistoricoActivity.this, ReceitasActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_historico) {
                    return true;
                }
                return false;
            }
        });
    }

    private void carregarTransacoes() {
        transacoes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_TRANSACAO, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
            double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valorTransacao"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow("despesaOuReceita"));

            transacoes.add(new Transacao(id, descricao, valor, data, tipo));
        }

        cursor.close();
        db.close();

        adapter = new TransacaoAdapter(this, transacoes, dbHelper, this::carregarTransacoes);
        listView.setAdapter(adapter);
    }

    public void carregarTotalGasto() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double totalGasto = 0;

        String query = "SELECT SUM(valorTransacao) FROM " + DBHelper.TABLE_TRANSACAO +
                " WHERE despesaOuReceita = 'despesa'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            totalGasto = cursor.getDouble(0);
        }

        cursor.close();

        textTotalGasto.setText(String.format("-R$%.2f", totalGasto));
    }

    public void carregarTotalGanho() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double totalGanho = 0;

        String query = "SELECT SUM(valorTransacao) FROM " + DBHelper.TABLE_TRANSACAO +
                " WHERE despesaOuReceita = 'receita'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            totalGanho = cursor.getDouble(0);
        }

        cursor.close();

        textTotalGanho.setText(String.format("+R$%.2f", totalGanho));
    }
}
