package com.eventos.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventos.eventos.model.Inscricao;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    Optional<Inscricao> findByEvento_IdAndUsuarioId(Long eventoId, Long usuarioId);
    int countByEvento_Id(Long eventoId);
}
