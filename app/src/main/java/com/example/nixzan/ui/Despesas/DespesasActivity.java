package com.example.nixzan.ui.Despesas;

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
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DespesasActivity extends AppCompatActivity {
    private TextView textTotalGasto;
    private Button btAddDespesa;
    private DBHelper dbHelper;
    private List<Transacao> transacoes;
    private ListView listView;
    private DespesasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        // Inicializando os componentes da UI
        btAddDespesa = findViewById(R.id.btAddDespesa);
        textTotalGasto = findViewById(R.id.textTotalGasto);
        listView = findViewById(R.id.listView);

        dbHelper = new DBHelper(this);

        // Configurar o botão de adicionar despesa
        btAddDespesa.setOnClickListener(v -> navigateToAddDespesas());

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_despesas);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                startActivity(new Intent(DespesasActivity.this, DashboardActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_historico) {
                startActivity(new Intent(DespesasActivity.this, HistoricoActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_receitas) {
                startActivity(new Intent(DespesasActivity.this, ReceitasActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_despesas) {
                return true;
            }
            return false;
        });

        carregarDespesas();
        carregarTotalGasto();
    }

    private void carregarDespesas() {
        transacoes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query para buscar apenas transações do tipo 'despesa'
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_TRANSACAO + " WHERE despesaOuReceita = 'despesa'", null);

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
        adapter = new DespesasAdapter(this, transacoes, dbHelper, this::carregarDespesas);
        listView.setAdapter(adapter);
    }

    public void carregarTotalGasto() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double totalGasto = 0;

        // Query para somar os valores das transações do tipo 'despesa'
        String query = "SELECT SUM(valorTransacao) FROM " + DBHelper.TABLE_TRANSACAO +
                " WHERE despesaOuReceita = 'despesa'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            totalGasto = cursor.getDouble(0);
        }
        cursor.close();
        db.close();

        // Atualiza o texto do total gasto
        textTotalGasto.setText(String.format("-R$%.2f", totalGasto));
    }

    // Método para navegar até a tela de adicionar despesa
    private void navigateToAddDespesas() {
        startActivity(new Intent(DespesasActivity.this, AddDespesaActivity.class));
        finish();
    }
}
