package com.eventos.eventos.service;

import com.eventos.eventos.model.Usuario;
import com.eventos.eventos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByNomeUsuarioOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com: " + username));

        return new User(usuario.getEmail(), usuario.getSenha(), new ArrayList<>());
    }
}