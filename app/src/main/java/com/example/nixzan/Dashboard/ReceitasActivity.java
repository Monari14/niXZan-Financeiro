package com.example.nixzan.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ReceitasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Marcar o ícone de Receitas como selecionado inicialmente
        bottomNavigationView.setSelectedItemId(R.id.nav_receitas);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    // Quando clicar em "Dashboard", abre a tela de Dashboard
                    startActivity(new Intent(ReceitasActivity.this, DashboardActivity.class));
                    return true;
                } else if (id == R.id.nav_receitas) {
                    // Se já estamos na tela de Receitas, não faz nada
                    return true;
                } else if (id == R.id.nav_despesas) {
                    // Quando clicar em "Despesas", abre a tela de Despesas
                    startActivity(new Intent(ReceitasActivity.this, DespesasActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
