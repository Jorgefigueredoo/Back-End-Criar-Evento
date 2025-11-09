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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}