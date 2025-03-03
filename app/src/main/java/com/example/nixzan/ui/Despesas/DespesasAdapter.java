package com.example.nixzan.ui.Despesas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nixzan.R;
import com.example.nixzan.ui.Despesas.Despesas.Despesa;

import java.util.List;

public class DespesasAdapter extends ArrayAdapter<Despesa> {

    public DespesasAdapter(Context context, List<Despesa> despesas) {
        super(context, 0, despesas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_despesa, parent, false);
        }

        Despesa despesa = getItem(position);

        TextView textDescricao = convertView.findViewById(R.id.editDescricao);
        TextView textCategoria = convertView.findViewById(R.id.spinnerCategoria);
        TextView textValor = convertView.findViewById(R.id.editValorAdd);
        TextView textData = convertView.findViewById(R.id.calendarView);

        textDescricao.setText(despesa.getDescricao());
        textCategoria.setText(despesa.getCategoria());
        textValor.setText("R$ " + despesa.getValor());
        textData.setText(despesa.getData());

        return convertView;
    }
}
