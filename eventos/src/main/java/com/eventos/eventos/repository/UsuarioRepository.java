package com.eventos.eventos.repository;

import com.eventos.eventos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNomeUsuarioOrEmail(String nomeUsuario, String email);

    Boolean existsByNomeUsuario(String nomeUsuario);

    Boolean existsByEmail(String email);
}