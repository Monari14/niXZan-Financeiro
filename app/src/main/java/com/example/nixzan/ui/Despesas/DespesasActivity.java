package com.example.nixzan.ui.Despesas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.R;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DespesasActivity extends AppCompatActivity {
    private TextView textTotalGasto;
    private Button btAddDespesa;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        // Botão para adicionar despesa
        btAddDespesa = findViewById(R.id.btAddDespesa);
        btAddDespesa.setOnClickListener(v -> navigateToAddDespesas());
        textTotalGasto = findViewById(R.id.textTotalGasto);
        dbHelper = new DBHelper(this);

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_despesas);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
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
            }
        });
        carregarTotalGasto();
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
        textTotalGasto.setText(String.format("Total Gasto R$%.2f", totalGasto));
    }
    private void navigateToAddDespesas() {
        startActivity(new Intent(DespesasActivity.this, AddDespesaActivity.class));
        finish();
    }
}
