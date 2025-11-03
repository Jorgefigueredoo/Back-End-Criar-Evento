package com.eventos.eventos.controller;

import com.eventos.eventos.model.Evento;
import com.eventos.eventos.model.Inscricao;
import com.eventos.eventos.repository.EventoRepository;
import com.eventos.eventos.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/eventos")
public class EventoInscricaoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    // POST /api/eventos/{id}/inscricoes
    @PostMapping("/{id}/inscricoes")
    public ResponseEntity<?> inscreverUsuario(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        Long usuarioId = Long.valueOf(body.get("usuarioId").toString());

        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null) {
            return ResponseEntity.badRequest().body(Map.of("erro", "EVENTO_NAO_ENCONTRADO"));
        }

        // Verifica se já está inscrito
        boolean jaInscrito = inscricaoRepository.findByEvento_IdAndUsuarioId(id, usuarioId).isPresent();
        if (jaInscrito) {
            return ResponseEntity.badRequest().body(Map.of("erro", "JA_INSCRITO"));
        }

        // Verifica vagas
        int totalInscritos = inscricaoRepository.countByEvento_Id(id);
        if (evento.getVagas() != null && totalInscritos >= evento.getVagas()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "VAGAS_ESGOTADAS"));
        }

        // Salva inscrição
        Inscricao nova = new Inscricao(usuarioId, evento);
        inscricaoRepository.save(nova);

        // Atualiza contagem de vagas disponíveis, se quiser
        if (evento.getVagas() != null) {
            evento.setVagas(evento.getVagas() - 1);
            eventoRepository.save(evento);
        }

        return ResponseEntity.ok(Map.of(
                "mensagem", "Inscrição realizada com sucesso!",
                "eventoId", id
        ));
    }

    // GET /api/eventos/{id}/inscricoes/{usuarioId}/status
    @GetMapping("/{id}/inscricoes/{usuarioId}/status")
    public ResponseEntity<Map<String, Object>> verificarStatusInscricao(
            @PathVariable Long id,
            @PathVariable Long usuarioId
    ) {
        Map<String, Object> status = new HashMap<>();

        boolean jaInscrito = inscricaoRepository.findByEvento_IdAndUsuarioId(id, usuarioId).isPresent();
        Evento evento = eventoRepository.findById(id).orElse(null);

        status.put("jaInscrito", jaInscrito);
        status.put("esgotado", evento != null && evento.getVagas() != null &&
                inscricaoRepository.countByEvento_Id(id) >= evento.getVagas());
        status.put("prazoExpirado", false); // pode implementar lógica de prazo aqui

        return ResponseEntity.ok(status);
    }
}
