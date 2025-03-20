package com.example.nixzan.Model;

public class Historico {

    private int id;
    private String senha;

    public Historico(int id, String senha) {
        this.id = id;
        this.senha = senha;
    }
    public int getId() {
        return id;
    }
    public String getSenha() {
        return senha;
    }

}