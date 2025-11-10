package com.eventos.eventos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- MUDANÇA AQUI ---
    // Em vez de 'private Long usuarioId;'
    // Nós ligamos a entidade Usuario diretamente.
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    // ---------------------

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "id")
    private Evento evento;

    private LocalDateTime dataInscricao = LocalDateTime.now();

    public Inscricao() {
    }

    // Construtor atualizado para receber o Objeto Usuario
    public Inscricao(Usuario usuario, Evento evento) {
        this.usuario = usuario;
        this.evento = evento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    // --- GETTERS E SETTERS ATUALIZADOS ---
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    // ------------------------------------

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}