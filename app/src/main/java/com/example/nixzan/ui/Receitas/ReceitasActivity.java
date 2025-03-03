package com.example.nixzan.ui.Receitas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.Model.Transacao;
import com.example.nixzan.R;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.example.nixzan.ui.Despesas.DespesasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ReceitasActivity extends AppCompatActivity {
    private TextView textTotalGanho;
    private Button btAddReceita;
    private DBHelper dbHelper;
    private List<Transacao> transacoes;
    private ListView listView;
    private ReceitasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        // Inicializando os componentes da UI
        btAddReceita = findViewById(R.id.btAddReceita);
        textTotalGanho = findViewById(R.id.textTotalGanho);
        listView = findViewById(R.id.listView);

        dbHelper = new DBHelper(this);

        btAddReceita.setOnClickListener(v -> navigateToAddReceitas());

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_receitas);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                startActivity(new Intent(ReceitasActivity.this, DashboardActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_historico) {
                startActivity(new Intent(ReceitasActivity.this, HistoricoActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_despesas) {
                startActivity(new Intent(ReceitasActivity.this, DespesasActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_receitas) {
                return true;
            }
            return false;
        });

        carregarReceitas();
        carregarTotalGanho();
    }

    private void carregarReceitas() {
        transacoes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_TRANSACAO + " WHERE despesaOuReceita = 'receita'", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
            double valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valorTransacao"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow("despesaOuReceita"));

            // Adiciona a transação à lista
            transacoes.add(new Transacao(id, descricao, valor, data, tipo));
        }

        cursor.close();
        db.close();

        // Cria o adapter e configura na ListView
        adapter = new ReceitasAdapter(this, transacoes, dbHelper, this::carregarReceitas);
        listView.setAdapter(adapter);
    }

    private void carregarTotalGanho() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double totalGasto = 0;

        String query = "SELECT SUM(valorTransacao) FROM " + DBHelper.TABLE_TRANSACAO +
                " WHERE despesaOuReceita = 'receita'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            totalGasto = cursor.getDouble(0);
        }
        cursor.close();
        db.close();

        textTotalGanho.setText(String.format("Total Ganho R$%.2f", totalGasto));
    }

    private void navigateToAddReceitas() {
        startActivity(new Intent(ReceitasActivity.this, AddReceitaActivity.class));
        finish();
    }
}
