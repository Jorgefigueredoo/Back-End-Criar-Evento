package com.eventos.eventos.repository;

import com.eventos.eventos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Métodos para buscar o usuário pelo nome de usuário OU pelo e-mail
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
    Optional<Usuario> findByEmail(String email);

    // Método para o Spring Security usar (pode ser o mesmo de cima)
    Optional<Usuario> findByNomeUsuarioOrEmail(String nomeUsuario, String email);

    // Métodos para verificar se já existem
    Boolean existsByNomeUsuario(String nomeUsuario);
    Boolean existsByEmail(String email);
}