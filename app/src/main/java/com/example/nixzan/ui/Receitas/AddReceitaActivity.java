package com.example.nixzan.ui.Receitas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.R;
import com.example.nixzan.ui.Despesas.DespesasActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddReceitaActivity extends AppCompatActivity {

    private EditText editValorReceitaAdd, editDescricaoReceita;
    private Spinner spinnerCategoriaReceita;
    private Button btSalvarReceita;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);

        // Inicializa os componentes
        dbHelper = new DBHelper(this);
        editValorReceitaAdd = findViewById(R.id.editValorReceitaAdd);
        editDescricaoReceita = findViewById(R.id.editDescricaoReceita);
        spinnerCategoriaReceita = findViewById(R.id.spinnerCategoriaReceita);
        btSalvarReceita = findViewById(R.id.btAddReceitaAdd);

        btSalvarReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarReceita();
            }
        });
    }

    private void salvarReceita() {
        String valorString = editValorReceitaAdd.getText().toString().trim();
        String descricao = editDescricaoReceita.getText().toString().trim();
        String categoriaSelecionada = spinnerCategoriaReceita.getSelectedItem().toString();

        // Verifica se o valor foi preenchido
        if (valorString.isEmpty()) {
            Toast.makeText(this, "O valor da Receita é obrigatório!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Converte o valor e verifica se é maior que zero
        double valor;
        try {
            valor = Double.parseDouble(valorString.replace(",", "."));
            if (valor <= 0) {
                Toast.makeText(this, "O valor deve ser maior que 0!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se descrição não for preenchida, deixa vazia
        if (descricao.isEmpty()) {
            descricao = "Sem descrição";
        }

        // Se categoria não for preenchida, define como "Outros"
        if (categoriaSelecionada.isEmpty()) {
            categoriaSelecionada = "Outros";
        }

        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Inserir a transação como DESPESA no banco
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TRANSACAO_VALOR, valor);
        values.put(DBHelper.COLUMN_TRANSACAO_DESCRICAO, descricao);
        values.put(DBHelper.COLUMN_TRANSACAO_METODO, categoriaSelecionada);
        values.put(DBHelper.COLUMN_TRANSACAO_DATA, data);
        values.put(DBHelper.COLUMN_TRANSACAO_DESPESA_OU_RECEITA, "receita");

        long result = db.insert(DBHelper.TABLE_TRANSACAO, null, values);

        if (result != -1) {
            atualizarSaldo(valor);
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddReceitaActivity.this, ReceitasActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarSaldo(double valorReceita) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Buscar o saldo atual
        String query = "SELECT * FROM " + DBHelper.TABLE_SALDO + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            double saldoAtual = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_SALDO_VALOR));
            double novoSaldo = saldoAtual - valorReceita;

            // Atualiza o saldo no banco
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.COLUMN_SALDO_VALOR, novoSaldo);
            db.update(DBHelper.TABLE_SALDO, contentValues, "_id = 1", null);
        }
        cursor.close();
    }
}
