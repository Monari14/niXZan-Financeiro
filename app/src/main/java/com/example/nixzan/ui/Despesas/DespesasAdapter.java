package com.example.nixzan.ui.Despesas;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.Model.Transacao;
import com.example.nixzan.R;
import com.example.nixzan.ui.Historico.HistoricoActivity;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class DespesasAdapter extends BaseAdapter {
    private Context context;
    private List<Transacao> transacoes;
    private DBHelper dbHelper;
    private Runnable atualizarValores;

    public DespesasAdapter(Context context, List<Transacao> transacoes, DBHelper dbHelper, Runnable atualizarValores) {
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
        MaterialButton excluirBtn = convertView.findViewById(R.id.btnExcluir);

        Transacao transacao = transacoes.get(position);
        if ("despesa".equalsIgnoreCase(transacao.getDespesaOuReceita())) {
            descricao.setText(transacao.getDescricao() + " | " + transacao.getData());
            valor.setText(String.format("R$ %.2f", transacao.getValor()));
            convertView.setBackgroundColor(Color.parseColor("#ed7171"));
        }

        excluirBtn.setOnClickListener(v -> {
            dbHelper.deletarTransacao(transacao.getId());
            transacoes.remove(position);
            notifyDataSetChanged();

            atualizarValores.run();

            if (context instanceof HistoricoActivity) {
                HistoricoActivity historicoActivity = (HistoricoActivity) context;
                historicoActivity.carregarTotalGasto();
            }
        });
        return convertView;
    }
}
