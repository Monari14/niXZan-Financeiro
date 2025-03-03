package com.example.nixzan.ui.Despesas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DespesasActivity extends AppCompatActivity {
    private Button btAddDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        btAddDespesa = findViewById(R.id.btAddDespesa);

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_despesas);

        btAddDespesa.setOnClickListener(v -> {
            navigateToAddDespesas();
        });
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
    }
    private void navigateToAddDespesas() {
        Intent intent = new Intent(DespesasActivity.this, AddDespesaActivity.class);
        startActivity(intent);
        finish();
    }
}
