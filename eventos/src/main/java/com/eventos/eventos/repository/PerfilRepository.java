package com.eventos.eventos.repository;

import com.eventos.eventos.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByUsuarioId(Long usuarioId);

    Optional<Perfil> findFirstByNomeCompletoContainingIgnoreCase(String nome);

    @Query("SELECT DISTINCT p FROM Perfil p LEFT JOIN p.habilidades h WHERE " +
            "LOWER(p.nomeCompleto) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(h) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Perfil> searchByNomeOuHabilidades(@Param("query") String query);
}