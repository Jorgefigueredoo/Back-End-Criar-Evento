package com.eventos.eventos;

import com.eventos.eventos.model.Usuario;
import com.eventos.eventos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EventosApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventosApplication.class, args);
    }

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            
            
            if (usuarioRepository.findByEmail("teste@email.com").isEmpty()) {
                System.out.println("--- Criando usuário 'teste@email.com' ---");
                Usuario usuarioTeste = new Usuario();
                usuarioTeste.setNomeUsuario("testuser");
                usuarioTeste.setEmail("teste@email.com");
                usuarioTeste.setSenha(passwordEncoder.encode("123456"));
                usuarioRepository.save(usuarioTeste);
                System.out.println(">>> Usuário de teste criado: teste@email.com / senha: 123456");
            } else {
                System.out.println("--- Usuário 'teste@email.com' já existe. ---");
            }

            
            if (usuarioRepository.findByEmail("jorge@email.com").isEmpty()) {
                System.out.println("--- Criando usuário 'jorge@email.com' ---");
                Usuario usuarioJorge = new Usuario();
                usuarioJorge.setNomeUsuario("jorgeuser");
                usuarioJorge.setEmail("jorge@email.com");
                usuarioJorge.setSenha(passwordEncoder.encode("12345")); 
                usuarioRepository.save(usuarioJorge);
                System.out.println(">>> Usuário 'jorge' criado: jorge@email.com / senha: 12345");
            } else {
                System.out.println("--- Usuário 'jorge@email.com' já existe. ---");
            }
        };
    }
}