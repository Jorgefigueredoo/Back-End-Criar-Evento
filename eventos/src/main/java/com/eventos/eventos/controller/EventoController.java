package com.eventos.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eventos.eventos.model.Evento;
import com.eventos.eventos.repository.EventoRepository;
import java.util.List;

import java.util.Optional; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EventoController {
    
    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping("/criar")
    public Evento criarEvento(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoPorId(@PathVariable Long id) {
        
        Optional<Evento> evento = eventoRepository.findById(id);

        if (evento.isPresent()) {
            return ResponseEntity.ok(evento.get()); 
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
