package com.example.nixzan.ui.Historico;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.R;
import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.Model.Historico;
import com.example.nixzan.ui.GeradorSenhas.GeradorActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    private ListView listView;
    private HistoricoAdapter adapter;
    private List<Historico> historicos;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);

        // Configurar a BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_historico);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    startActivity(new Intent(HistoricoActivity.this, GeradorActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.nav_historico) {
                    return true;
                }
                return false;
            }
        });
    }

    private void carregarHistorico() {
        historicos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_SENHA, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha"));


            historicos.add(new Historico(id, senha));
        }

        cursor.close();
        db.close();

        adapter = new HistoricoAdapter(this, historicos, dbHelper, this::carregarHistorico);
        listView.setAdapter(adapter);
    }
}
