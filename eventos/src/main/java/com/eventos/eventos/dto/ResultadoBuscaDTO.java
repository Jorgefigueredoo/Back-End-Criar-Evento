package com.eventos.eventos.dto;

public class ResultadoBuscaDTO {
    private String nome;
    private String descricao;
    private String urlPerfil;

    public ResultadoBuscaDTO(String nome, String descricao, String urlPerfil) {
        this.nome = nome;
        this.descricao = descricao;
        this.urlPerfil = urlPerfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlPerfil() {
        return urlPerfil;
    }

    public void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }
}