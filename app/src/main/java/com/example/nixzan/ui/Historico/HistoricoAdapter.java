package com.example.nixzan.ui.Historico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nixzan.Database.DBHelper;
import com.example.nixzan.Model.Historico;
import com.example.nixzan.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HistoricoAdapter extends BaseAdapter {
    private Context context;
    private List<Historico> historicos;
    private DBHelper dbHelper;
    private Runnable atualizarValores;

    public HistoricoAdapter(Context context, List<Historico> transacoes, DBHelper dbHelper, Runnable atualizarValores) {
        this.context = context;
        this.historicos = historicos;
        this.dbHelper = dbHelper;
        this.atualizarValores = atualizarValores;
    }

    @Override
    public int getCount() {
        return historicos.size();
    }

    @Override
    public Object getItem(int position) {
        return historicos.get(position);
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
        MaterialButton excluirBtn = convertView.findViewById(R.id.btnExcluir);

        Historico hist = historicos.get(position);
        descricao.setText(hist.getSenha());

        excluirBtn.setOnClickListener(v -> {
            dbHelper.deletarSenha(hist.getId());
            historicos.remove(position);
            notifyDataSetChanged();

            atualizarValores.run();
        });


        return convertView;
    }
}
