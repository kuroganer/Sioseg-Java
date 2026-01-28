package com.sioseg.controllers;

import com.sioseg.models.Usuario;
import com.sioseg.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class FixPasswordController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/fix-password")
    public String fixPassword() {
        try {
            // Remove usuário existente se houver
            usuarioRepository.deleteAll();
            
            // Cria novo usuário
            Usuario admin = new Usuario();
            admin.setNomeUsu("Admin Teste");
            admin.setCpfUsu("12345678901");
            admin.setRgUsu("1234567");
            admin.setRgEmissorUsu("SSP-DF");
            admin.setDataExpedicaoRgUsu(LocalDate.of(2020, 1, 1));
            admin.setDataNascimentoUsu(LocalDate.of(1990, 1, 1));
            admin.setDataCadastroUsu(LocalDateTime.now());
            admin.setTel1Usu("61999999999");
            admin.setEmailUsu("admin@teste.com");
            admin.setSenhaHashUsu(passwordEncoder.encode("123456"));
            admin.setPerfil("admin");
            admin.setStatus("ativo");
            
            usuarioRepository.save(admin);
            
            return "Usuario criado com sucesso!<br>" +
                   "Email: admin@teste.com<br>" +
                   "Senha: 123456<br>" +
                   "<a href='/login'>Fazer Login</a>";
                   
        } catch (Exception e) {
            return "Erro ao criar usuario: " + e.getMessage();
        }
    }
}