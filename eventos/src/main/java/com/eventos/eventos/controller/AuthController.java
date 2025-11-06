package com.eventos.eventos.controller;

import com.eventos.eventos.config.JwtTokenUtil; // IMPORTE
import com.eventos.eventos.dto.LoginRequest;
import com.eventos.eventos.dto.LoginResponse;
import com.eventos.eventos.model.Usuario;
import com.eventos.eventos.repository.UsuarioRepository;
import com.eventos.eventos.service.UserDetailsServiceImpl; // IMPORTE
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // IMPORTE
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // IMPORTE
import org.springframework.security.core.userdetails.UserDetails; // IMPORTE
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // INJETE O AUTH MANAGER

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // INJETE O UTIL DE JWT

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // INJETE O USERDETAILS

    @Autowired
    private UsuarioRepository usuarioRepository; // (Necessário para pegar email/id)

    @Autowired
    private PasswordEncoder passwordEncoder; // (Mantenha para o endpoint de registro, se tiver)


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

        // Autentica usando o Spring Security
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Erro: Usuário ou senha inválidos!");
        }

        // Se a autenticação foi bem-sucedida, busca os detalhes do usuário
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getLogin());

        // Gera o token JWT DE VERDADE
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Busca o usuário no banco para pegar o ID e Email (para a resposta)
        Usuario usuario = usuarioRepository.findByNomeUsuarioOrEmail(loginRequest.getLogin(), loginRequest.getLogin()).get();

        // Retorna o token real
        return ResponseEntity.ok(new LoginResponse(
            token,
            usuario.getId(),
            usuario.getNomeUsuario(),
            usuario.getEmail()
        ));
    }

    // (Aqui você teria seu endpoint de /register para criar usuários)
}