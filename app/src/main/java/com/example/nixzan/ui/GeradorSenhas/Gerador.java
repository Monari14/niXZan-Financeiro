package com.example.nixzan.ui.GeradorSenhas;

import java.security.SecureRandom;

public class Gerador {
    public static String gerarSenha(int tamanho) {
        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>? ";

        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(tamanho);

        for (int i = 0; i < tamanho; i++) {
            int indiceAleatorio = random.nextInt(caracteresPermitidos.length());
            senha.append(caracteresPermitidos.charAt(indiceAleatorio));
        }

        return senha.toString();
    }
}
