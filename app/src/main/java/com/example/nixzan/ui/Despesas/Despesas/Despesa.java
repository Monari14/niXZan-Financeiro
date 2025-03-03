package com.example.nixzan.ui.Despesas.Despesas;

import android.os.Parcel;
import android.os.Parcelable;

public class Despesa implements Parcelable {
    private String data;
    private float valor;
    private String descricao;
    private String categoria;

    public Despesa(String data, float valor, String descricao, String categoria) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Métodos Parcelable
    protected Despesa(Parcel in) {
        data = in.readString();
        valor = in.readFloat();
        descricao = in.readString();
        categoria = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeFloat(valor);
        dest.writeString(descricao);
        dest.writeString(categoria);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Despesa> CREATOR = new Parcelable.Creator<Despesa>() {
        @Override
        public Despesa createFromParcel(Parcel in) {
            return new Despesa(in);
        }

        @Override
        public Despesa[] newArray(int size) {
            return new Despesa[size];
        }
    };
}
