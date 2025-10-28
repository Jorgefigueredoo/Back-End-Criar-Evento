package com.eventos.eventos.controller;

import com.eventos.eventos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public String login(@RequestBody LoginRequest request) {
        boolean autenticado = usuarioService.autenticar(request.getLogin(), request.getSenha());
        return autenticado ? "Login realizado com sucesso!" : "Credenciais inválidas!";
    }

    public static class LoginRequest {
        private String login;
        private String senha;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
