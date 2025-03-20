package com.example.nixzan.ui.GeradorSenhas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.Database.SenhaData;
import com.example.nixzan.R;

import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class GeradorActivity extends AppCompatActivity {

    private SenhaData senhaData;
    private EditText editTextNumero;
    private Button btProsseguir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumero = findViewById(R.id.editTextNumero);
        btProsseguir = findViewById(R.id.btAdd);

        senhaData = new SenhaData(this);

        btProsseguir.setOnClickListener(v -> {
            String numeroStr = editTextNumero.getText().toString();
            if (!numeroStr.isEmpty()) {
                try {
                    int numero = Integer.parseInt(numeroStr);
                    Gerador g = new Gerador();
                    String senha = g.gerarSenha(numero);
                    System.out.println(senha + " | gorda");
                    senhaData.saveSenha(senha);

                    Toast.makeText(GeradorActivity.this, "Senha salva!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(GeradorActivity.this, "Digite um número válido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(GeradorActivity.this, "Digite um número válido", Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    return true;
                } else if (id == R.id.nav_historico) {
                    startActivity(new Intent(GeradorActivity.this, HistoricoActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
