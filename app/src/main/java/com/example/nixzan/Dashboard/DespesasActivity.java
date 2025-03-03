package com.example.nixzan.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.Dashboard.DashboardActivity;
import com.example.nixzan.Dashboard.ReceitasActivity;
import com.example.nixzan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DespesasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Marcar o ícone de Despesas como selecionado inicialmente
        bottomNavigationView.setSelectedItemId(R.id.nav_despesas);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    // Quando clicar em "Dashboard", abre a tela de Dashboard
                    startActivity(new Intent(DespesasActivity.this, DashboardActivity.class));
                    return true;
                } else if (id == R.id.nav_receitas) {
                    // Quando clicar em "Receitas", abre a tela de Receitas
                    startActivity(new Intent(DespesasActivity.this, ReceitasActivity.class));
                    return true;
                } else if (id == R.id.nav_despesas) {
                    // Se já estamos na tela de Despesas, não faz nada
                    return true;
                }
                return false;
            }
        });
    }
}
