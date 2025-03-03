package com.example.nixzan.ui.Despesas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nixzan.ui.Despesas.Despesas.DadosDespesa;
import com.example.nixzan.R;
import com.example.nixzan.ui.Despesas.Despesas.Despesa;

public class AddDespesaActivity extends AppCompatActivity {

    private EditText editTextValor, editTextDescricao;
    private Spinner spinnerCategoria;  // Adicionando o Spinner para pegar a categoria
    private Button buttonSalvar;
    private DadosDespesa dadosDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_despesa);

        // Inicializando os componentes da UI
        editTextValor = findViewById(R.id.editValorAdd);
        editTextDescricao = findViewById(R.id.editDescricao);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);  // Inicializando o Spinner
        buttonSalvar = findViewById(R.id.btAddDespesa);

        // Inicializando o banco de dados
        dadosDespesa = new DadosDespesa(this);

        // Ação ao clicar no botão de salvar
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDespesa();
            }
        });
    }

    // Método para salvar a despesa
    private void salvarDespesa() {
        String valorString = editTextValor.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();  // Pegando a categoria selecionada

        if (valorString.isEmpty() || Float.parseFloat(valorString) <= 0) {
            Toast.makeText(this, "Valor deve ser maior que 0!", Toast.LENGTH_SHORT).show();
            return;
        }

        float valor = Float.parseFloat(valorString);
        String dataDespesa = obterDataDespesa();  // Obter a data selecionada ou a data atual
        dadosDespesa.salvarDespesa(dataDespesa, valor, descricao, categoria);  // Salva a despesa no banco de dados

        // Cria o objeto Despesa
        Despesa novaDespesa = new Despesa(dataDespesa, valor, descricao, categoria);

        // Passa o objeto Despesa para a próxima Activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("novaDespesa", novaDespesa);
        setResult(RESULT_OK, resultIntent);
        finish();

        Toast.makeText(this, "Despesa salva com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private String obterDataDespesa() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date data = new java.util.Date();
        return sdf.format(data); // Retorna a data atual no formato desejado
    }
}
