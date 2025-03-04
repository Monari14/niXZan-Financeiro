package com.example.nixzan.Model;

public class Transacao {
    private int id;
    private String descricao, data;
    private double valor;
    private String despesaOuReceita;

    public Transacao(int id, String descricao, double valor, String data, String despesaOuReceita) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.despesaOuReceita = despesaOuReceita;
    }

    public String getDespesaOuReceita() {
        return despesaOuReceita;
    }

    public int getId() {
            return id;
        }

        public String getDescricao() {
            return descricao;
        }
        public String getData() {
            return data;
        }

        public double getValor() {
            return valor;
        }
    }
