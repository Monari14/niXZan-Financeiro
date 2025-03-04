package com.example.nixzan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.ui.Dashboard.DashboardActivity;
import com.example.nixzan.Database.UserData;

public class MainActivity extends AppCompatActivity {

    private UserData userData;
    private EditText editNome;
    private Button btProsseguir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os elementos da tela
        editNome = findViewById(R.id.editNome);
        btProsseguir = findViewById(R.id.btProsseguir);

        userData = new UserData(this);

        // Verificar se o nome já está salvo no banco de dados
        String savedName = userData.getUserName();
        if (savedName != null && !savedName.isEmpty()) {
            // Se o nome já estiver salvo, ir direto para a Dashboard
            navigateToDashboard(savedName);
        }

        // Configurar o clique no botão "Prosseguir"
        btProsseguir.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();

            if (nome.isEmpty()) {
                editNome.setError("Digite seu nome");
                return;
            }

            // Salvar o nome no banco de dados
            userData.saveUserName(nome);

            // Mostrar mensagem de sucesso
            Toast.makeText(this, "Nome salvo com sucesso!", Toast.LENGTH_SHORT).show();

            // Ir para a DashboardActivity e passar o nome do usuário
            navigateToDashboard(nome);
        });
    }

    private void navigateToDashboard(String nome) {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        intent.putExtra("userName", nome);
        startActivity(intent);
        finish();
    }
}
