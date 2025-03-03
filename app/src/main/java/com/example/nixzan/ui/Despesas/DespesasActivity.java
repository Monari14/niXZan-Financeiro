package com.example.nixzan.ui.Despesas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.ui.Despesas.Despesas.Despesa;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.example.nixzan.ui.Receitas.ReceitasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class DespesasActivity extends AppCompatActivity {

    private Button btAddDespesa;
    private ListView listView;
    private DespesasAdapter despesaAdapter;
    private List<Despesa> listaDespesas;
    private float totalDespesas = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        listaDespesas = new ArrayList<>();
        listView = findViewById(R.id.listView);
        btAddDespesa = findViewById(R.id.btAddDespesa);

        // Configuração do adapter da lista
        despesaAdapter = new DespesasAdapter(this, listaDespesas);
        listView.setAdapter(despesaAdapter);

        btAddDespesa.setOnClickListener(v -> navigateToAddDespesas());

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
    }

    private void navigateToAddDespesas() {
        startActivity(new Intent(DespesasActivity.this, AddDespesaActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Recuperando a nova despesa adicionada
            Despesa novaDespesa = (Despesa) data.getSerializableExtra("novaDespesa");

            // Adicionando a nova despesa à lista
            listaDespesas.add(novaDespesa);
            totalDespesas += novaDespesa.getValor();

            // Atualizando a lista e o total
            despesaAdapter.notifyDataSetChanged();
            atualizarTotalDespesasUI();
        }
    }

    // Atualiza o TextView com o total das despesas
    private void atualizarTotalDespesasUI() {
        TextView totalTextView = findViewById(R.id.textTotalGasto);
        totalTextView.setText("Total Gasto R$ " + totalDespesas);
    }
}
