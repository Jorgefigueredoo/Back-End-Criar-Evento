package com.eventos.eventos.controller;

import com.eventos.eventos.model.Perfil;
import com.eventos.eventos.model.Usuario;
import com.eventos.eventos.dto.PerfilUpdateDto;
import com.eventos.eventos.repository.PerfilRepository;
import com.eventos.eventos.repository.UsuarioRepository;
import com.eventos.eventos.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfis")
@CrossOrigin(origins = "*")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/me")
    public ResponseEntity<?> getMeuPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("erro", "Usuário não autenticado"));
        }

        Optional<Perfil> perfilOpt = perfilRepository.findByUsuarioId(usuario.getId());
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("erro", "Perfil não encontrado para este usuário"));
        }

        return ResponseEntity.ok(perfilOpt.get());
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMeuPerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PerfilUpdateDto perfilUpdateDto
    ) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("erro", "Usuário não autenticado"));
        }

        Optional<Perfil> perfilOpt = perfilRepository.findByUsuarioId(usuario.getId());
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("erro", "Perfil não encontrado para atualizar"));
        }

        Perfil perfil = perfilOpt.get();
        perfil.setNomeCompleto(perfilUpdateDto.getNomeCompleto());
        perfil.setTitulo(perfilUpdateDto.getTitulo());
        perfil.setSobreMim(perfilUpdateDto.getSobreMim());
        perfil.setHabilidades(perfilUpdateDto.getHabilidades());

        Perfil perfilSalvo = perfilRepository.save(perfil);
        return ResponseEntity.ok(perfilSalvo);
    }

    @PostMapping("/foto")
    public ResponseEntity<?> uploadFotoPerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file
    ) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("erro", "Usuário não autenticado"));
        }

        Optional<Perfil> perfilOpt = perfilRepository.findByUsuarioId(usuario.getId());
        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("erro", "Perfil não encontrado"));
        }

        try {
            String novaUrl = fileStorageService.salvarArquivo(file);

            Perfil perfil = perfilOpt.get();
            perfil.setFotoPerfilUrl(novaUrl);
            perfilRepository.save(perfil);

            return ResponseEntity.ok(Map.of("novaUrl", novaUrl));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Falha ao salvar a foto: " + e.getMessage()));
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getPerfilPublico(@PathVariable Long usuarioId) {
        Optional<Perfil> perfilOpt = perfilRepository.findByUsuarioId(usuarioId);

        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("erro", "Perfil não encontrado"));
        }

        return ResponseEntity.ok(perfilOpt.get());
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPerfilPorNome(@RequestParam("nome") String nome) {
        Optional<Perfil> perfilOpt = perfilRepository.findFirstByNomeCompletoContainingIgnoreCase(nome);

        if (perfilOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Usuário não encontrado."));
        }

        return ResponseEntity.ok(perfilOpt.get());
    }

    private Usuario buscarUsuarioLogado(UserDetails userDetails) {
        if (userDetails == null) {
            return null;
        }
        String login = userDetails.getUsername();
        return usuarioRepository.findByNomeUsuarioOrEmail(login, login).orElse(null);
    }
}
