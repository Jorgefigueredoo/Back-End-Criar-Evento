package com.eventos.eventos.dto;

public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nomeUsuario;
    private String email;

    public LoginResponse(String token, Long id, String nomeUsuario, String email) {
        this.token = token;
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}