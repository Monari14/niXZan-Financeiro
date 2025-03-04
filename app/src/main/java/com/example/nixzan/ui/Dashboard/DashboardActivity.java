package com.example.nixzan.ui.Dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.R;
import com.example.nixzan.ui.Despesas.DespesasActivity;
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    private TextView textTotalGasto, textTotalGanho, textSaldo, textViewNome;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textViewNome = findViewById(R.id.textViewNome);
        textTotalGasto = findViewById(R.id.textTotalGasto);
        textTotalGanho = findViewById(R.id.textTotalGanho);
        textSaldo = findViewById(R.id.textSaldo);
        dbHelper = new DBHelper(this);

        // Buscar o nome do usuário no banco
        String userName = getUserNameFromDB();
        textViewNome.setText(userName != null ? userName : "Usuário");

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    return true;
                } else if (id == R.id.nav_receitas) {
                    startActivity(new Intent(DashboardActivity.this, ReceitasActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_despesas) {
                    startActivity(new Intent(DashboardActivity.this, DespesasActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_historico) {
                    startActivity(new Intent(DashboardActivity.this, HistoricoActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

        carregarTotalGasto();
        carregarTotalGanho();
        carregarSaldo();
    }

    private String getUserNameFromDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String userName = null;

        String query = "SELECT " + DBHelper.COLUMN_USER_NAME + " FROM " + DBHelper.TABLE_USER + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }

        cursor.close();
        return userName;
    }

    private void carregarTotalGasto() {
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

    private void carregarTotalGanho() {
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

    private void carregarSaldo() {
        carregarTotalGanho();
        carregarTotalGasto();

        double totalGanho = Double.parseDouble(textTotalGanho.getText().toString().replace("+R$", "").replace(",", "."));
        double totalGasto = Double.parseDouble(textTotalGasto.getText().toString().replace("-R$", "").replace(",", "."));

        double saldo = totalGanho - totalGasto;

        textSaldo.setText(String.format("Saldo R$%.2f", saldo));
    }
}
