package com.sioseg.config;

import com.sioseg.models.Usuario;
import com.sioseg.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                // Criar usuário de teste se não existir
                if (!usuarioRepository.findByEmailUsu("teste@teste.com").isPresent()) {
                    Usuario teste = new Usuario();
                    teste.setNomeUsu("Usuario Teste");
                    teste.setCpfUsu("99999999999");
                    teste.setRgUsu("9999999");
                    teste.setRgEmissorUsu("SSP-DF");
                    teste.setDataExpedicaoRgUsu(LocalDate.of(2020, 1, 1));
                    teste.setDataNascimentoUsu(LocalDate.of(1990, 1, 1));
                    teste.setDataCadastroUsu(LocalDateTime.now());
                    teste.setTel1Usu("61999999999");
                    teste.setEmailUsu("teste@teste.com");
                    teste.setSenhaHashUsu(passwordEncoder.encode("123"));
                    teste.setPerfil("admin");
                    teste.setStatus("ativo");
                    
                    usuarioRepository.save(teste);
                    
                    System.out.println("\n=== CREDENCIAIS DE TESTE ===");
                    System.out.println("Email: teste@teste.com");
                    System.out.println("Senha: 123");
                    System.out.println("============================\n");
                }
            } catch (Exception e) {
                System.out.println("Erro ao criar usuário teste: " + e.getMessage());
            }
        };
    }
}