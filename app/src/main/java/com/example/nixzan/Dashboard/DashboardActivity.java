package com.example.nixzan.Dashboard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Recuperar o nome do usuário do Intent
        String userName = getIntent().getStringExtra("userName");

        // Exibir o nome no TextView
        TextView textViewNome = findViewById(R.id.textViewNome);
        if (userName != null) {
            textViewNome.setText(userName);
        } else {
            textViewNome.setText("");
        }
    }
}
