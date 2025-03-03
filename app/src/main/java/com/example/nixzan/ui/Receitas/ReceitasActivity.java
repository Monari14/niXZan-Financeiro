package com.example.nixzan.ui.Receitas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Despesas.DespesasActivity;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ReceitasActivity extends AppCompatActivity {

    private Button btAddReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        // Botão para adicionar despesa
        btAddReceita = findViewById(R.id.btAddReceita);
        btAddReceita.setOnClickListener(v -> navigateToAddReceita());

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_receitas);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
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
            }
        });
    }

    private void navigateToAddReceita() {
        startActivity(new Intent(ReceitasActivity.this, AddReceitaActivity.class));
        finish();
    }
}
