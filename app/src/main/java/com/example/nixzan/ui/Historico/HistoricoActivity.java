package com.example.nixzan.ui.Historico;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Despesas.DespesasActivity;
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HistoricoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

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
                } else if (id == R.id.nav_receitas) {
                    startActivity(new Intent(HistoricoActivity.this, ReceitasActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_despesas) {
                    startActivity(new Intent(HistoricoActivity.this, DespesasActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_historico) {
                    return true;
                }
                return false;
            }
        });
    }
}
