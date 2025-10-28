package com.eventos.eventos.service;

import com.eventos.eventos.model.Usuario;
import com.eventos.eventos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean autenticar(String login, String senha) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(login);

        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioRepository.findByNome(login);
        }

        return usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha);
    }
}
