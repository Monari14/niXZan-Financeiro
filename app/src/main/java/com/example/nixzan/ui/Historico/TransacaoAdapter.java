package com.example.nixzan.ui.Historico;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.Model.Transacao;
import com.example.nixzan.R;

import java.util.List;

public class TransacaoAdapter extends BaseAdapter {
    private Context context;
    private List<Transacao> transacoes;
    private DBHelper dbHelper;
    private Runnable atualizarValores;

    public TransacaoAdapter(Context context, List<Transacao> transacoes, DBHelper dbHelper, Runnable atualizarValores) {
        this.context = context;
        this.transacoes = transacoes;
        this.dbHelper = dbHelper;
        this.atualizarValores = atualizarValores;
    }

    @Override
    public int getCount() {
        return transacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return transacoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_transacao, parent, false);
        }

        TextView descricao = convertView.findViewById(R.id.descricao);
        TextView valor = convertView.findViewById(R.id.valor);
        Button btnExcluir = convertView.findViewById(R.id.btnExcluir);  // Botão de exclusão

        // Obter a transação correspondente à posição
        Transacao transacao = transacoes.get(position);

        // Definir a descrição e o valor da transação
        descricao.setText(transacao.getDescricao() + " | " + transacao.getData());
        valor.setText(String.format("R$ %.2f", transacao.getValor()));

        // Definir a cor de fundo dependendo se é uma receita ou despesa
        if ("receita".equalsIgnoreCase(transacao.getDespesaOuReceita()))
            convertView.setBackgroundColor(Color.parseColor("#92f5af"));
        else {
            convertView.setBackgroundColor(Color.parseColor("#ed7171"));
        }

        // Ação para o botão de excluir
        btnExcluir.setOnClickListener(v -> {
            excluirTransacao(transacao);
        });

        return convertView;
    }

    // Método para excluir a transação do banco de dados
    private void excluirTransacao(Transacao transacao) {
        // Excluir do banco de dados
        dbHelper.deletarTransacao(transacao.getId());

        // Remover a transação da lista local
        transacoes.remove(transacao);
        notifyDataSetChanged();  // Atualizar a lista na interface

        // Atualizar o saldo
        atualizarValores.run();

        // Exibir mensagem de confirmação
        Toast.makeText(context, "Excluído!", Toast.LENGTH_SHORT).show();
    }
}
