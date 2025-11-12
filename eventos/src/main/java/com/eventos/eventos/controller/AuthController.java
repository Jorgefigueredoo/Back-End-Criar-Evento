package com.eventos.eventos.controller;

import com.eventos.eventos.config.JwtTokenUtil;
import com.eventos.eventos.dto.LoginRequest;
import com.eventos.eventos.dto.LoginResponse;
import com.eventos.eventos.dto.RegisterDto;
import com.eventos.eventos.model.Usuario;
import com.eventos.eventos.model.Perfil;
import com.eventos.eventos.repository.UsuarioRepository;
import com.eventos.eventos.repository.PerfilRepository;
import com.eventos.eventos.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Erro: Usuário ou senha inválidos!");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);

        Usuario usuario = usuarioRepository.findByNomeUsuarioOrEmail(loginRequest.getLogin(), loginRequest.getLogin())
                .get();

        return ResponseEntity.ok(new LoginResponse(
                token,
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterDto registerDto) {

        if (usuarioRepository.findByNomeUsuarioOrEmail(registerDto.getNomeUsuario(), registerDto.getEmail())
                .isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Nome de usuário ou e-mail já está em uso!");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeUsuario(registerDto.getNomeUsuario());
        novoUsuario.setEmail(registerDto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(registerDto.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Perfil novoPerfil = new Perfil();
        novoPerfil.setUsuario(usuarioSalvo);
        novoPerfil.setNomeCompleto(registerDto.getNomeCompleto());
        novoPerfil.setTitulo(registerDto.getTitulo());
        novoPerfil.setSobreMim(registerDto.getSobreMim());
        novoPerfil.setHabilidades(registerDto.getHabilidades());

        perfilRepository.save(novoPerfil);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(registerDto.getNomeUsuario());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(
                token,
                usuarioSalvo.getId(),
                usuarioSalvo.getNomeUsuario(),
                usuarioSalvo.getEmail()));
    }

}