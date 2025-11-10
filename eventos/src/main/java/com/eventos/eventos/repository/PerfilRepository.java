package com.eventos.eventos.repository;

import com.eventos.eventos.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

   Optional<Perfil> findByUsuarioId(Long usuarioId);
    
    Optional<Perfil> findFirstByNomeCompletoContainingIgnoreCase(String nome);
}