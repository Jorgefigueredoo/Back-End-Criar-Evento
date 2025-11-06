package com.eventos.eventos.dto;

import jakarta.validation.constraints.NotBlank;

// Esta classe representa o JSON que o front-end enviará
public class LoginRequest {

    @NotBlank
    // "Nome do usuário / E-mail"
    private String login;

    @NotBlank
    private String senha;

    // Gere Getters e Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}